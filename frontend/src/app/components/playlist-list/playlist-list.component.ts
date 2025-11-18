// src/app/components/playlist/playlist.component.ts
import { Component } from '@angular/core';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../models/playlist.model';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, switchMap } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist-list.component.html',
  styleUrl: './playlist-list.component.scss', 
  imports: [FormsModule, ReactiveFormsModule, CommonModule]
})
export class PlaylistComponent {
  playlists: Playlist[] = [];
  searchControl = new FormControl('');
  newPlaylistTitle = '';

  constructor(private playlistService: PlaylistService) {
    // Load all playlists initially
    this.playlistService.getAll().subscribe(list => this.playlists = list);

    // Listen to search input
    this.searchControl.valueChanges.pipe(
      debounceTime(300),
      switchMap(query => this.playlistService.search(query || ''))
    ).subscribe(filtered => this.playlists = filtered);
  }

  addPlaylist() {
    if (!this.newPlaylistTitle.trim()) return;
    this.playlistService.add(this.newPlaylistTitle.trim()).subscribe(newPlaylist => {
      this.playlists.push(newPlaylist);
      this.newPlaylistTitle = '';
    });
  }
}
