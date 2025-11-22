import { Component, computed, effect, Input, OnInit, SimpleChanges } from '@angular/core';
import { PlaylistService } from '../../services/playlist.service';
import { Playlist } from '../../models/playlist.model';
import { Song } from '../../models/song.model';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { KeycloakService } from '../../services/keycloak/keycloak.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { debounceTime } from 'rxjs';
import { sortBy } from '../../util/utils';

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

  searchQuery = toSignal(
    this.searchControl.valueChanges.pipe(debounceTime(300)),
    { initialValue: '' }
  );


  // 2. Utilise le signal de query pour filtrer via ton service
  filteredSongs = () => {
    const q = this.searchQuery()?.toLowerCase() ?? '';
    const list = this.playlist!.items.filter(s =>
      s.song.title.toLowerCase().includes(q)
    );
    return list
  };

  constructor(private playlistService: PlaylistService, private keycloakService: KeycloakService) {
    this.playlist = this.playlistService.current_playlist();
  }

  async ngOnInit(): Promise<void> {

      const all = this.playlistService.playlists();
      if (all.length === 0) return;
    
      const pl = await this.playlistService.getById(`${this.playlistId}`);
      if (pl) {
        // assure que songs est toujours un tableau
        this.playlist = { ...pl, items: pl.items ?? [] };
        console.log("Loaded playlist:", this.playlist);
      }

  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['playlistId']) {
      this.updatePlaylist();
    }
  }

  async updatePlaylist() {
    const pl = await this.playlistService.getById(this.playlistId);
    this.playlist = { ...pl, items: pl?.items ?? [] };
    //console.log(`Loaded Playlist : ${JSON.stringify(this.playlist)}`)
  }

  async loadPlaylist() {
    console.log(`Playlist number: ${this.playlistId}`);
    console.log(`Playlist: ${this.playlistService.getById(`1`)}`);
    this.playlist = await this.playlistService.getById(`${this.playlistId}`)!;
  }

  async addSong() {
    if (!this.newSongTitle.trim() || !this.playlist) return;
    const song: Song = {
      title: this.newSongTitle.trim(),
      owner: this.keycloakService.getUserName(), // adjust according to your user info
    };
    (await this.playlistService.addSongToPlaylist(this.playlist.id!, song)).subscribe(() => {
      this.newSongTitle = '';
      this.loadPlaylist();
    });
  }

  removeSong(songId: string) {
    /* if (!this.playlist) return;
    this.playlistService.removeSongFromPlaylist(this.playlist.id, songId).subscribe(() => {
      this.loadPlaylist();
    }); */
  }

}
