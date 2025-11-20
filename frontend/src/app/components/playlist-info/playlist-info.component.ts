import { Component, effect, Input, OnInit, SimpleChanges } from '@angular/core';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../models/playlist.model';
import { Song } from '../../models/song.model';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-playlist-info',
  templateUrl: './playlist-info.component.html',
  styleUrls: ['./playlist-info.component.scss'],
  imports: [FormsModule, ReactiveFormsModule, CommonModule]
})
export class PlaylistInfoComponent implements OnInit {
  @Input() playlistId!: string;
  playlist?: Playlist;
  newSongTitle = '';
  searchControl = new FormControl('');

  constructor(private playlistService: PlaylistService) {
    effect(() => {
      const all = this.playlistService.playlists();
      if (all.length === 0) return;
    
      const pl = this.playlistService.getById(`${this.playlistId}`);
      if (pl) {
        // assure que songs est toujours un tableau
        this.playlist = { ...pl, songs: pl.songs ?? [] };
        console.log("Loaded playlist:", this.playlist);
      }
    });
  }

  ngOnInit(): void {
    

    // Optional: filter songs
    this.searchControl.valueChanges.subscribe(query => {
      if (!this.playlist) return;
      if (!query) return;
      this.playlist.songs = this.playlist.songs.filter(s =>
        s.song.title.toLowerCase().includes(query.toLowerCase())
      );
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['playlistId']) {
      this.updatePlaylist();
    }
  }

  updatePlaylist() {
    const pl = this.playlistService.getById(this.playlistId);
    this.playlist = { ...pl, songs: pl?.songs ?? [] };
  }

  loadPlaylist() {
    console.log(`Playlist number: ${this.playlistId}`);
    console.log(`Playlist: ${this.playlistService.getById(`1`)}`);
    this.playlist = this.playlistService.getById(`${this.playlistId}`)!;
  }

  addSong() {
    /* if (!this.newSongTitle.trim() || !this.playlist) return;
    const song: Song = {
      id: Date.now().toString(),
      title: this.newSongTitle.trim(),
      owner: 'You', // adjust according to your user info
    };
    this.playlistService.addSongToPlaylist(this.playlist.id, song).subscribe(() => {
      this.newSongTitle = '';
      this.loadPlaylist();
    }); */
  }

  removeSong(songId: string) {
    /* if (!this.playlist) return;
    this.playlistService.removeSongFromPlaylist(this.playlist.id, songId).subscribe(() => {
      this.loadPlaylist();
    }); */
  }
}
