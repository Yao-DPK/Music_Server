import { Component, computed, inject, Input, OnInit, signal, Signal, SimpleChanges } from '@angular/core';
import { PlayerService } from '../../services/player.service';
import { Song } from '../../models/song.model';
import { AsyncPipe } from '@angular/common';


@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss'],
  imports: [AsyncPipe]
})
export class PlayerComponent{
  @Input({ required: true }) songs!: Signal<Song[]>;
  @Input() songId?: string;
  playerService = inject(PlayerService);

  readonly isPlaying = this.playerService.getIsPlaying();

  readonly currentSong = computed(() => {
    return this.playerService.currentSong();
  });
  
  togglePlayPause() {
      if (this.isPlaying()) this.playerService.pause();
      else this.playerService.play();
 
  }

  next() {
    /* this.playerService.nextTrack(); // à implémenter */
    console.log("Suivant");
  }

  prev() {
    /* this.playerService.prevTrack(); // à implémenter */
    console.log("Précedent");
  }
}
