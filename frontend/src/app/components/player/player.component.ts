import { Component, computed, ElementRef, inject, Input, OnInit, signal, Signal, SimpleChanges, ViewChild } from '@angular/core';
import { PlayerService } from '../../services/player.service';
import { Song } from '../../models/song.model';
import { AsyncPipe, NgForOf } from '@angular/common';
import { PlaylistItem } from '../../models/playlistItem.model';


@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss'],
  imports: [AsyncPipe, NgForOf]
})
export class PlayerComponent{
  @Input({ required: true }) songs!: Signal<Song[]>;
  @Input() songId?: string;
  playerService = inject(PlayerService);

  @ViewChild('audioPlayer') audioPlayer!: ElementRef<HTMLAudioElement>;

  readonly isPlaying = this.playerService.getIsPlaying();
  readonly currentSong = this.playerService.currentSong;
  readonly currentQueue = this.playerService.getQueue();
  
  togglePlayPause() {
      if (this.isPlaying()) this.playerService.pause();
      else this.playerService.play();
 
  }

  next() {
    this.playerService.nextTrack(); // à implémenter 
    this.playCurrent();
    console.log("Suivant");
  }

  prev() {
    this.playerService.prevTrack(); // à implémenter 
    this.playCurrent();
    console.log("Précedent");
  }

  playSong(song: PlaylistItem) {
    this.playerService.currentSongId.set(song.id!);
    this.playCurrent();
  }


  private playCurrent() {
    const song = this.currentSong();
    if (!song) return;

    const player = this.audioPlayer.nativeElement;
    // URL points directly to Spring Boot static file
    player.src = `http://localhost:8097/Down-Bad.mp3`; 
    player.load();
    player.play();
    this.playerService.play();
  }
}
