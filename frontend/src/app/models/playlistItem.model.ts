import { Playlist } from "./playlist.model";
import { Song } from "./song.model";

// src/app/models/playlist.model.ts
export class PlaylistItem {
    id?: string;
    playlist: string;
    song: Song;
    position: number;

    constructor(playlist: string, song: Song, position: number, id?: string){
      this.playlist = playlist;
      this.song = song;
      this.position = position;
      this.id = id;
  } 
    
    
    

  // Factory method to create a Song instance from a plain object
static fromDto(dto: any): PlaylistItem {
  return new PlaylistItem(
    dto.id,
    dto.playlist,
    dto.song,
    dto.position
  );
}

static createPlaylistItem(playlist: Playlist, song: Song): PlaylistItem {
  return new PlaylistItem(
    playlist.id!,
    song,
    playlist.items.length
  );
}

  }
  