// src/app/services/playlist.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Playlist } from '../models/playlist.model';
import { Song } from '../models/song.model';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class PlaylistService {
  private playlists: Playlist[] = [
    { id: '1', title: 'Rock Classics', songs: [] },
    { id: '2', title: 'Chill Vibes', songs: [] },
    { id: '3', title: 'Workout Mix', songs: [] },
  ];

  private playlists$ = new BehaviorSubject<Playlist[]>(this.playlists);

  getAll(): Observable<Playlist[]> {
    return this.playlists$.asObservable();
  }

  getById(id: string): Observable<Playlist | undefined> {
    return this.playlists$.pipe(
      map(list => list.find(p => p.id === id))
    );
  }

  search(query: string): Observable<Playlist[]> {
    return this.playlists$.pipe(
      map(list => list.filter(p => p.title.toLowerCase().includes(query.toLowerCase())))
    );
  }

  add(title: string): Observable<Playlist> {
    const newPlaylist: Playlist = {
      id: (this.playlists.length + 1).toString(),
      title,
      createdAt: new Date(),
      songs: []
    };
    this.playlists.push(newPlaylist);
    this.playlists$.next(this.playlists);
    return of(newPlaylist);
  }

  addSongToPlaylist(playlistId: string, song: Song): Observable<Song> {
    const playlist = this.playlists.find(p => p.id === playlistId);
    if (playlist) {
      playlist.songs.push(song);
      this.playlists$.next(this.playlists);
      return of(song);
    }
    return of(undefined as any);
  }

  removeSongFromPlaylist(playlistId: string, songId: string): Observable<void> {
    const playlist = this.playlists.find(p => p.id === playlistId);
    if (playlist) {
      playlist.songs = playlist.songs.filter(s => s.id !== songId);
      this.playlists$.next(this.playlists);
    }
    return of();
  }
}
