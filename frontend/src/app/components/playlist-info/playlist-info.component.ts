import { Component, Input, OnInit } from '@angular/core';
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

  constructor(private playlistService: PlaylistService) {}

  ngOnInit(): void {
    this.loadPlaylist();

    // Optional: filter songs
    this.searchControl.valueChanges.subscribe(query => {
      if (!this.playlist) return;
      if (!query) return;
      this.playlist.songs = this.playlist.songs.filter(s =>
        s.title.toLowerCase().includes(query.toLowerCase())
      );
    });
  }

  loadPlaylist() {
    this.playlistService.getById(this.playlistId).subscribe(p => {
      this.playlist = p;
    });
  }

  addSong() {
    if (!this.newSongTitle.trim() || !this.playlist) return;
    const song: Song = {
      id: Date.now().toString(),
      title: this.newSongTitle.trim(),
      owner: 'You', // adjust according to your user info
    };
    this.playlistService.addSongToPlaylist(this.playlist.id, song).subscribe(() => {
      this.newSongTitle = '';
      this.loadPlaylist();
    });
  }

  removeSong(songId: string) {
    if (!this.playlist) return;
    this.playlistService.removeSongFromPlaylist(this.playlist.id, songId).subscribe(() => {
      this.loadPlaylist();
    });
  }
}
