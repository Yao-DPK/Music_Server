import { Component, inject, OnInit, signal } from '@angular/core';
import { PlaylistService } from '../../services/playlist.service';
import { PlayerComponent } from "../../components/player/player.component";
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  providers:[PlaylistService]
})
export class HomeComponent{
  playlistService = inject(PlaylistService);
  playlist = signal([]);
  router = inject(Router);

  goToStream(){
    this.router.navigate(["/test"])
  }

}
