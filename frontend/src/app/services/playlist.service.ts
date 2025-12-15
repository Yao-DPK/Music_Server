// src/app/services/playlist.service.ts
import { computed, inject, Injectable, signal } from '@angular/core';
import {firstValueFrom, of } from 'rxjs';
import { Playlist } from '../models/playlist.model';
import { Song } from '../models/song.model';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { KeycloakService } from './keycloak/keycloak.service';
import { PlaylistItem } from '../models/playlistItem.model';

@Injectable({ providedIn: 'root' })
export class PlaylistService {

  private http = inject(HttpClient);
  private keycloak = inject(KeycloakService);

  readonly playlists = signal<Playlist[]>([]);
  currentPlaylistId = signal<string | null>(null);

  readonly currentPlaylist = computed(() => {
    const id = this.currentPlaylistId();
    return this.playlists().find(p => p.id === id) ?? null;
  });

  async loadAll(): Promise<void> {
    try {
      const list = await firstValueFrom(
        this.http.get<Playlist[]>(`${environment.apiUrl}/playlists/me`).pipe(
          catchError(err => {
            console.error(err);
            return of([]);
          })
        )
      );

      const playlists = Array.isArray(list)
        ? list.map(dto => Playlist.fromDto(dto))
        : [];

      this.playlists.set(playlists);
      console.log("Received Playlists: ", playlists);

      if (!this.currentPlaylistId() && playlists.length) {
        const defaultPl = playlists.find(p => p.title === 'All Songs') ?? playlists[0];
        this.currentPlaylistId.set(defaultPl.id!);
      }
      console.log("Currently loaded playlist: ", this.currentPlaylist());

    } catch (e) {
      console.error('Failed to load playlists', e);
      this.playlists.set([]);
    }
  }

  setCurrentPlaylist(id: string) {
    this.currentPlaylistId.set(id);
  }

  async add(title: string) {
    const payload = {
      title,
      creator: this.keycloak.getUserName(),
    };

    const created = await firstValueFrom(
      this.http.post<Playlist>(`${environment.apiUrl}/playlists/me`, payload)
    );

    await this.loadAll;
  }

  addSong(playlistId: string, song: Song) {
    this.playlists.update(pls =>
      pls.map(pl =>
        pl.id !== playlistId
          ? pl
          : {
              ...pl,
              items: [
                ...(pl.items ?? []),
                PlaylistItem.createPlaylistItem(pl, song)
              ]
          }
      )
    );
  }

  searchPlaylists(query: string): Playlist[] {
    return this.playlists().filter(p =>
      p.title.toLowerCase().includes(query.toLowerCase())
    );
  }
} 
