// src/app/components/playlist/playlist.component.ts
import { Component, computed, effect, EventEmitter, inject, OnInit, Output, Signal, signal } from '@angular/core';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../models/playlist.model';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, switchMap } from 'rxjs/operators';
import { toSignal } from '@angular/core/rxjs-interop';
import { CommonModule } from '@angular/common';
import { sortBy } from '../../util/utils';
import { title } from 'process';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist-list.component.html',
  styleUrl: './playlist-list.component.scss', 
  imports: [FormsModule, ReactiveFormsModule, CommonModule]
})
export class PlaylistListComponent implements OnInit {
  playlists : Signal<Playlist[]>;
  searchControl = new FormControl('');
  newPlaylistTitle = signal('');
  @Output() currentPlaylistId = new EventEmitter<string>();

  constructor(private playlistService: PlaylistService) {
    this.playlists = this.playlistService.playlists;
  }


  searchQuery = toSignal(
    this.searchControl.valueChanges.pipe(debounceTime(300)),
    { initialValue: '' }
  );

  // 2. Utilise le signal de query pour filtrer via ton service
  filteredPlaylists = computed(() => {
    const q = this.searchQuery()?.toLowerCase() ?? '';
    const list = this.playlists().filter(p =>
      p.title.toLowerCase().includes(q)
    );
  
    return sortBy([...list], "title", false);
  });

  
  ngOnInit(): void {
    this.playlistService.loadAll();
    //console.log(`Voici la liste actuelle des playlists: ${this.playlistService.playlists()}`);
  }
  
  selectPlaylist(id: string){
    console.log("Changing Playlist");
    this.currentPlaylistId.emit(id);
    this.playlistService.currentPlaylistId.set(id);
  }
  
  addPlaylist() {
    //console.log("Addind nex Playlist");
    if (!this.newPlaylistTitle().trim()) return;
    this.playlistService.add(this.newPlaylistTitle().trim());
  }
}
