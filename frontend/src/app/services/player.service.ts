// src/app/services/player.service.ts
import { computed, inject, Injectable, signal, Signal, effect } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Song } from '../models/song.model';
import { PlaylistItem } from '../models/playlistItem.model';
import { PlaylistService } from './playlist.service';
import { Playlist } from '../models/playlist.model';
//import { Song } from '../models/song.model';

@Injectable({ providedIn: 'root' })
export class PlayerService {
  
  playlistService = inject(PlaylistService);
  private queue = signal<PlaylistItem[]>([]);
  currentSongId = signal<string | null>(null);
  private isPlaying = signal<boolean>(false);
  private currentIndex = signal<number>(0);

  readonly currentSong = computed(() => {
    const id = this.currentSongId();
    return this.playlistService.currentPlaylist()?.items.find(p => p.id === id) ?? null;
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
    if (this.currentSong()) this.isPlaying.set(true);
  }

  pause() {
    if (this.currentSong()) this.isPlaying.set(false);
  }

  // === Gestion de la queue ===
  setQueue(songs: PlaylistItem[]) {
    this.queue.set(songs);
    this.play();
  }

  getQueue(){
    return this.queue;
  }

  nextTrack() {
    if (this.currentIndex() < this.queue().length - 1) {
      console.log('Courrent Index: ', this.currentIndex());
      this.currentIndex.update(val => val + 1);
      this.currentSongId.set(this.queue()![this.currentIndex()].id!);
      this.play();
    } else {
      this.pause(); // fin de la queue
    }
  }

  prevTrack() {
    if (this.currentIndex() > 0) {
      console.log('Courrent Index: ', this.currentIndex());
      this.currentIndex.update(val => val - 1);
      this.currentSongId.set(this.queue()![this.currentIndex()].id!);
      this.play();
    } else{
      this.pause();
    }
  }


  // === Reset player ===
  reset() {
    this.currentSongId.set(this.playlistService.currentPlaylist()!.items[0].id!);
    this.queue.set([]);
    this.currentIndex.set(-1);
  } 
}
