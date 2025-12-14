// src/app/services/player.service.ts
import { computed, inject, Injectable, signal, Signal } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Song } from '../models/song.model';
import { PlaylistItem } from '../models/playlistItem.model';
import { PlaylistService } from './playlist.service';
import { Playlist } from '../models/playlist.model';
//import { Song } from '../models/song.model';

@Injectable({ providedIn: 'root' })
export class PlayerService {
  
  playlistService = inject(PlaylistService);
  currentSongId = signal<string | null>(null)
  private isPlaying = signal<boolean>(false);
  private currentIndex = -1;

  readonly queue = computed(() => {
    if(this.playlistService.currentPlaylist()){
      return this.playlistService.currentPlaylist()?.items!;
    } else {
      return [];
    }
  })
  
  readonly currentSong = computed(() => {
    const id = this.currentSongId();
    return this.queue()!.find(p => p.id === id) ?? null;
  });


  // === Observables pour les composants ===
  getCurrentTrack(): Signal<PlaylistItem | null> {
    return this.currentSong;
  }

  getIsPlaying(): Signal<boolean> {
    return this.isPlaying;
  }

  // === Contrôles du player ===
  play() {
    if (this.currentSong()!.song) this.isPlaying.set(true);
  }

  pause() {
    if (this.currentSong()!.song) this.isPlaying.set(false);
  }

  /* // === Gestion de la queue ===
  setQueue(songs: PlaylistItem[], startIndex: number = 0) {
    this.queue.set(songs);
    this.currentIndex = startIndex;
    this.currentSong.set(this.queue()[this.currentIndex]);
    this.play();
  } */

  /* nextTrack() {
    if (this.currentIndex < this.queue.length - 1) {
      this.currentIndex++;
      this.currentSong.set(this.queue()![this.currentIndex]);
      this.play();
    } else {
      this.pause(); // fin de la queue
    }
  }

  prevTrack() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
      this.currentSong.set(this.queue()![this.currentIndex]);
      this.play();
    }
  }

  selectTrack(id: string) {
    const index = this.queue()!.findIndex(t => t.id === id);
    if (index !== -1) {
      this.currentIndex = index;
    } else {
      this.queue().push(song);
      this.currentIndex = this.queue.length - 1;
    }
    this.currentSong.set(this.queue()[this.currentIndex]);
    this.play;
  }

  // === Reset player ===
  reset() {
    this.currentSong.set(this.playlistService.currentPlaylist()!.items[0]);
    this.pause;
    this.queue.set([]);
    this.currentIndex = -1;
  } */
}
