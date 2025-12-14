import { Component, inject, OnInit, signal } from '@angular/core';
import { PlayerComponent } from "../../components/player/player.component";
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { tap } from 'rxjs';
import { AudioService } from '../../services/song.service';
import { Song } from '../../models/song.model';
import { PlayerService } from '../../services/player.service';
import { PlaylistListComponent } from "../../components/playlist-list/playlist-list.component";
import { PlaylistInfoComponent } from "../../components/playlist-info/playlist-info.component";
import { KeycloakService } from '../../services/keycloak/keycloak.service';
import { Playlist } from '../../models/playlist.model';
import { PlaylistService } from '../../services/playlist.service';

@Component({
  selector: 'app-test-pages',
  imports: [PlayerComponent, PlaylistListComponent, PlaylistInfoComponent],
  templateUrl: './test-pages.component.html',
  styleUrl: './test-pages.component.scss'
})
export class TestPagesComponent implements OnInit{

  

  http = inject(HttpClient);
  playerService = inject(PlayerService);
  audioService = inject(AudioService);
  keycloakService = inject(KeycloakService);
  playlistService = inject(PlaylistService);
  apiUrl = environment.apiUrl;
  currentPlaylistId = signal<string>('1');
  currentSongId = signal<string>('1');
  current_songs= signal<Song[]>([]);

  ngOnInit() {
    //console.log("StremingPage");
    this.keycloakService.getUserName();
    this.audioService.getSongs().subscribe({
      next: (res) => {
        console.log("Receivd Songs: ", res)
        this.current_songs.set(res.map(dto => Song.fromDto(dto)));
        console.log('Songs loaded', this.current_songs());
      },
      error: (err) => console.error('Failed to load songs', err)
    });
  }

  onPlaylistChange($event: string) {
    this.currentPlaylistId.set($event);
  }

  onSongChange($event: string) {
    this.currentSongId.set($event);
  }
  

  uploadSong(body: FormData) {
    return this.http.post(`${this.apiUrl}/playlists/me/songs`, body).pipe(
      tap(response => console.log('response', response))
    );
  }
  
  onFileSelected(event: any){
    const file: File = event.target.files[0];

    if (!file) return;

    const formData = new FormData();
    //formData.append('file', file);
    formData.append('title', file.name);

    this.uploadSong(formData).subscribe({
      next: (res) => console.log("Upload success", res),
      error: (err) => console.error("Upload failed", err)
  });
  }
}
