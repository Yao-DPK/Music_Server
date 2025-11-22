// src/app/services/playlist.service.ts
import { computed, inject, Injectable, signal } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Playlist } from '../models/playlist.model';
import { Song } from '../models/song.model';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { KeycloakService } from './keycloak/keycloak.service';
import { PlaylistItem } from '../models/playlistItem.model';

@Injectable({ providedIn: 'root' })
export class PlaylistService {
  private http = inject(HttpClient);
  private keycloakService = inject(KeycloakService);

  playlists = signal<Playlist[]>([]);
  current_playlist = signal<Playlist>(this.playlists()[0]);

  // --- GET ALL ---
  async getAll(): Promise<void> {
    try {
      const list = await this.http
        .get<Playlist[]>(`${environment.apiUrl}/playlists/me`)
        .toPromise();
  
      const safeList = Array.isArray(list) ? list : [];

      if (safeList.find(p => p.title == "All Songs") == undefined){
        this.add("All Songs");
      }
      //console.log(`Safe List: ${safeList.find(p => p.title == "All Songs")}` );
      
      this.playlists.set(safeList.map(dto => Playlist.fromDto(dto)));

  
    } catch (err) {
      console.error('Failed to load playlists', err);
      this.playlists.set([]); // sécurité
    }
  }

  // --- GET BY ID ---
  async getById(id: string) {
    const play = await this.http
    .get<Playlist>(`${environment.apiUrl}/playlists/me/${id}`)
    .toPromise();
    return play ?? this.current_playlist();
  }
  

  // --- SEARCH (computed — réactif et propre) ---
  search = (query: string) => computed(() =>
    this.playlists()
      .filter(p => p.title.toLowerCase().includes(query.toLowerCase()))
  );

  // --- ADD ---
  async add(title: string): Promise<void>  {
    
    const payload = {
      title,
      creator: this.keycloakService.getUserName(),
      songs: []
    };

    const created = await this.http
      .post<Playlist>(`${environment.apiUrl}/playlists/me`, payload)
      .toPromise();

    // mise à jour du signal
    this.playlists.update(pls => [...pls, Playlist.fromDto(created)]);
  }


  async addSongToPlaylist(playlistId: string, song: Song) {
    const playlist = this.getById(playlistId);
    if (playlist) {
      const new_song: PlaylistItem  = PlaylistItem.createPlaylistItem(await playlist, song);
      (await playlist).items.push(new_song);
      return of(song);
    }
    return of(undefined as any);
  }

  removeSongFromPlaylist(playlistId: string, songId: string){
    /* const playlist = this.getById(playlistId);
    if (playlist) {
      playlist.songs = playlist.songs.filter(s => s.id !== songId);
      
    }
    return of(); */
  }
}
