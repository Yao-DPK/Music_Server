// src/app/services/player.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Song } from '../models/song.model';
//import { Song } from '../models/song.model';

@Injectable({ providedIn: 'root' })
export class PlayerService {
  private currentTrackSubject = new BehaviorSubject<Song | null>(null);
  private isPlayingSubject = new BehaviorSubject<boolean>(false);
  private queue: Song[] = [];
  private currentIndex = -1;

  constructor() {}

  // === Observables pour les composants ===
  getCurrentTrack(): Observable<Song | null> {
    return this.currentTrackSubject.asObservable();
  }

  getIsPlaying(): Observable<boolean> {
    return this.isPlayingSubject.asObservable();
  }

  // === Contrôles du player ===
  play() {
    if (this.currentTrackSubject.value) this.isPlayingSubject.next(true);
  }

  pause() {
    this.isPlayingSubject.next(false);
  }

  togglePlayPause() {
    const playing = this.isPlayingSubject.value;
    this.isPlayingSubject.next(!playing);
  }

  // === Gestion de la queue ===
  setQueue(songs: Song[], startIndex: number = 0) {
    this.queue = songs;
    this.currentIndex = startIndex;
    this.currentTrackSubject.next(this.queue[this.currentIndex]);
    this.isPlayingSubject.next(true);
  }

  nextTrack() {
    if (this.currentIndex < this.queue.length - 1) {
      this.currentIndex++;
      this.currentTrackSubject.next(this.queue[this.currentIndex]);
      this.isPlayingSubject.next(true);
    } else {
      this.pause(); // fin de la queue
    }
  }

  prevTrack() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
      this.currentTrackSubject.next(this.queue[this.currentIndex]);
      this.isPlayingSubject.next(true);
    }
  }

  selectTrack(song: Song) {
    const index = this.queue.findIndex(t => t.id === song.id);
    if (index !== -1) {
      this.currentIndex = index;
    } else {
      this.queue.push(song);
      this.currentIndex = this.queue.length - 1;
    }
    this.currentTrackSubject.next(this.queue[this.currentIndex]);
    this.isPlayingSubject.next(true);
  }

  // === Reset player ===
  reset() {
    this.currentTrackSubject.next(null);
    this.isPlayingSubject.next(false);
    this.queue = [];
    this.currentIndex = -1;
  }
}
