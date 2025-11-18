import { Component, OnInit } from '@angular/core';
import { PlayerService } from '../../services/player.service';
import { Observable } from 'rxjs';
import { Song } from '../../models/song.model';
import { AsyncPipe } from '@angular/common';


@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.scss'],
  imports: [AsyncPipe]
})
export class PlayerComponent implements OnInit {
  currentTrack$: Observable<Song | null>; 
  isPlaying$: Observable<boolean>;

  constructor(private playerService: PlayerService) {
    this.currentTrack$ = new Observable();
    this.isPlaying$ = this.playerService.getIsPlaying();
  }

  ngOnInit() {}

  togglePlayPause() {
    this.isPlaying$.subscribe(isPlaying => {
      if (isPlaying) this.playerService.pause();
      else this.playerService.play();
    }).unsubscribe();
  }

  next() {
    this.playerService.nextTrack(); // à implémenter
  }

  prev() {
    this.playerService.prevTrack(); // à implémenter
  }
}
