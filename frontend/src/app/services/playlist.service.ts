// src/app/services/playlist.service.ts
import { computed, inject, Injectable, signal } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Playlist } from '../models/playlist.model';
import { Song } from '../models/song.model';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { KeycloakService } from './keycloak/keycloak.service';

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
  
      this.playlists.set(safeList.map(dto => Playlist.fromDto(dto)));

  
    } catch (err) {
      console.error('Failed to load playlists', err);
      this.playlists.set([]); // sécurité
    }
  }

  // --- GET BY ID ---
  getById(id: string) {
    const play = this.playlists().find(p => p.id == id);
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


  addSongToPlaylist(playlistId: string, song: Song) {
    /* const playlist = this.playlists.find(p => p.id === playlistId);
    if (playlist) {
      playlist.songs.push(song);
      this.playlists$.next(this.playlists);
      return of(song);
    }
    return of(undefined as any); */
  }

  removeSongFromPlaylist(playlistId: string, songId: string){
    /* const playlist = this.playlists.find(p => p.id === playlistId);
    if (playlist) {
      playlist.songs = playlist.songs.filter(s => s.id !== songId);
      this.playlists$.next(this.playlists);
    }
    return of(); */
  }
}
