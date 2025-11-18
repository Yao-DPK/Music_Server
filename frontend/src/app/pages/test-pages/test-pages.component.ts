import { Component, inject, OnInit } from '@angular/core';
import { PlayerComponent } from "../../components/player/player.component";
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { tap } from 'rxjs';
import { AudioService } from '../../services/audio.service';
import { Song } from '../../models/song.model';
import { PlayerService } from '../../services/player.service';
import { PlaylistComponent } from "../../components/playlist-list/playlist-list.component";
import { PlaylistInfoComponent } from "../../components/playlist-info/playlist-info.component";

@Component({
  selector: 'app-test-pages',
  imports: [PlayerComponent, PlaylistComponent, PlaylistInfoComponent],
  templateUrl: './test-pages.component.html',
  styleUrl: './test-pages.component.scss'
})
export class TestPagesComponent implements OnInit{


  http = inject(HttpClient);
  playerService = inject(PlayerService);
  audioService = inject(AudioService);
  apiUrl = environment.apiUrl;
  current_playlist_id: string = '1';

  current_songs: Song[] = [];

  ngOnInit() {
    console.log("StremingPage");
    this.audioService.getSongs().subscribe({
      next: (res) => {
        this.current_songs = res.map(dto => Song.fromDto(dto));
        console.log('Songs loaded', this.current_songs);
      },
      error: (err) => console.error('Failed to load songs', err)
    });
  
  }



  uploadSong(body: FormData) {
    return this.http.post(`${this.apiUrl}/songs/me`, body).pipe(
      tap(response => console.log('response', response))
    );
  }
  
  onFileSelected(event: any){
    const file: File = event.target.files[0];

    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);
    formData.append('title', file.name);

    this.uploadSong(formData).subscribe({
      next: (res) => console.log("Upload success", res),
      error: (err) => console.error("Upload failed", err)
  });
  }
}
