import { Component, ElementRef, inject, Input, signal, ViewChild, AfterViewInit, Signal } from '@angular/core';
import { PlayerService } from '../../services/player.service';
import { Song } from '../../models/song.model';
import { PlaylistItem } from '../../models/playlistItem.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss'],
  imports: [CommonModule]
})
export class PlayerComponent implements AfterViewInit {
  @Input({ required: true }) songs!: Signal<Song[]>;
  @Input() songId?: string;
  playerService = inject(PlayerService);

  @ViewChild('audioPlayer') audioPlayer!: ElementRef<HTMLAudioElement>;

  readonly isPlaying = this.playerService.getIsPlaying();
  readonly currentSong = this.playerService.currentSong;
  readonly currentQueue = this.playerService.getQueue();

  // Signals for progress
  currentTime = signal(0);
  duration = signal(0);

  ngAfterViewInit() {
    const player = this.audioPlayer?.nativeElement;
    if (!player) return;

    // Update current time as audio plays
    player.ontimeupdate = () => {
      this.currentTime.set(player.currentTime);
    };

    // Update total duration when metadata is loaded
    player.onloadedmetadata = () => {
      this.duration.set(player.duration || 0);
    };
  }

  togglePlayPause() {
    const player = this.audioPlayer?.nativeElement;
    if (!player) return;

    if (!player.src) {
      // No song loaded yet
      this.playCurrent();
      return;
    }

    if (this.isPlaying()) {
      player.pause();
      this.playerService.pause();
    } else {
      player.play()
        .then(() => this.playerService.play())
        .catch(err => console.error('Audio play failed:', err));
    }
  }

  private playCurrent() {
    const song = this.currentSong();
    const player = this.audioPlayer?.nativeElement;
    if (!song || !player) return;

    // Only update src if song changed
    const src = `http://localhost:8097/${song.song.title}`;
    if (player.src !== src) {
      player.src = src;
      player.load(); // triggers onloadedmetadata
    }

    player.play()
      .then(() => this.playerService.play())
      .catch(err => console.error('Audio play failed:', err));
  }

  next() {
    this.playerService.nextTrack();
    this.playCurrent();
  }

  prev() {
    this.playerService.prevTrack();
    this.playCurrent();
  }

  playSong(song: PlaylistItem) {
    this.playerService.currentSongId.set(song.id!);
    this.playCurrent();
  }

  seek(event: Event) {
    const player = this.audioPlayer?.nativeElement;
    if (!player) return;

    const target = event.target as HTMLInputElement;
    const value = Number(target.value);

    player.currentTime = value;
    this.currentTime.set(value);
  }

  formatTime(time: number): string {
    if (!time) return '0:00';
    const minutes = Math.floor(time / 60);
    const seconds = Math.floor(time % 60);
    return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
  }
}
