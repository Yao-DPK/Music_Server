// src/app/components/playlist/playlist.component.ts
import { Component, computed, effect, EventEmitter, inject, OnInit, Output, signal } from '@angular/core';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../models/playlist.model';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, switchMap } from 'rxjs/operators';
import { toSignal } from '@angular/core/rxjs-interop';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist-list.component.html',
  styleUrl: './playlist-list.component.scss', 
  imports: [FormsModule, ReactiveFormsModule, CommonModule]
})
export class PlaylistListComponent implements OnInit {
  
  playlistService = inject(PlaylistService);
  playlists = this.playlistService.playlists;
  searchControl = new FormControl('');
  newPlaylistTitle = signal('');
  @Output() current_playlist_id = new EventEmitter<string>();


  searchQuery = toSignal(
    this.searchControl.valueChanges.pipe(debounceTime(300)),
    { initialValue: '' }
  );

  // 2. Utilise le signal de query pour filtrer via ton service
  filteredPlaylists = computed(() => {
    const q = this.searchQuery()?.toLowerCase() ?? '';
  
    return this.playlists().filter(p =>
      p.title.toLowerCase().includes(q)
    );
  });

  constructor() {}
  ngOnInit(): void {
    this.playlistService.getAll();
    console.log(`Voici la liste actuelle des playlists: ${this.playlistService.playlists()}`);
  }
  
  changeCurrentPlaylistid(id: string){
    console.log(`current_playlist: `);
    console.log(`Choosing a new Playlist`);
    this.current_playlist_id.emit(id);
    //return this.playlistService.current_playlist.set(this.playlistService.getById(id));
  }
  
  addPlaylist() {
    console.log("Addind nex Playlist");
    if (!this.newPlaylistTitle().trim()) return;
    this.playlistService.add(this.newPlaylistTitle().trim());
  }
}
