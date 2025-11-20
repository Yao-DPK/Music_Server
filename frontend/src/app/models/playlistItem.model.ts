import { Playlist } from "./playlist.model";
import { Song } from "./song.model";

// src/app/models/playlist.model.ts
export class PlaylistItem {
    id?: string;
    playlist: Playlist;
    song: Song;
    position: number;

    constructor(id: string, playlist: Playlist, song: Song, position: number){
      this.id = id;
      this.playlist = playlist;
      this.song = song;
      this.position = position;
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

  }
  