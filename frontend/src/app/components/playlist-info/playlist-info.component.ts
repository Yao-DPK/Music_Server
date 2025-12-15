import { Component, computed, effect, EventEmitter, inject, Input, OnInit, Output, Signal, SimpleChanges } from '@angular/core';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../models/playlist.model';
import { Song } from '../../models/song.model';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { KeycloakService } from '../../services/keycloak/keycloak.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { debounceTime } from 'rxjs';
import { sortBy } from '../../util/utils';
import { PlayerService } from '../../services/player.service';

@Component({
  selector: 'app-playlist-info',
  templateUrl: './playlist-info.component.html',
  styleUrls: ['./playlist-info.component.scss'],
  imports: [FormsModule, ReactiveFormsModule, CommonModule]
})
export class PlaylistInfoComponent{
  @Input({required: true}) playlistId!: Signal<string>;
  @Output() currentSongId = new EventEmitter<string>();
  
  newSongTitle = '';
  private playlistService = inject(PlaylistService);
  private keycloakService =  inject(KeycloakService);
  private playerService = inject(PlayerService);

  readonly playlist = computed(() => {
    return this.playlistService.currentPlaylist;
  });

  readonly searchControl = new FormControl('');
  searchQuery = toSignal(
    this.searchControl.valueChanges.pipe(debounceTime(300)),
    { initialValue: '' }
  );

  setQueue(){
    this.playerService.currentSongId.set(this.playlist()()!.items![0].id!);
    this.playerService.setQueue(this.playlist()()!.items!);
  }
  
  selectSong(id: string) {
    this.currentSongId.emit(id);
    this.playerService.currentSongId.set(id);
    this.playerService.setQueue(this.playlist()()!.items!);
  }

  // 2. Utilise le signal de query pour filtrer via ton service
  readonly filteredSongs = computed(() => {
    const q = this.searchQuery()?.toLowerCase() ?? '';
    const playlist = (this.playlist())();
    const items = playlist ? (playlist)?.items ?? [] : [];
    return items.filter(s =>
      s.song.title.toLowerCase().includes(q)
    );
  });

  

  async addSong() {
    if (!this.newSongTitle.trim() || !this.playlist) return;
    const song: Song = {
      title: this.newSongTitle.trim(),
      owner: this.keycloakService.getUserName(), // adjust according to your user info
    };
    (await this.playlistService.addSong((await this.playlist())()!.id!, song))
  }


  removeSong(songId: string) {
    /* if (!this.playlist) return;
    this.playlistService.removeSongFromPlaylist(this.playlist.id, songId).subscribe(() => {
      this.loadPlaylist();
    }); */
  }

}

