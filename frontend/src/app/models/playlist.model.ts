import { PlaylistItem } from "./playlistItem.model";
import { Song } from "./song.model";

// src/app/models/playlist.model.ts
export class Playlist {
    id?: string;
    title: string;
    creator: string;
    songs: PlaylistItem[];

    constructor(id: string, title: string, creator: string, songs: PlaylistItem[]){
      this.id = id;
      this.title = title;
      this.creator = creator;
      this.songs = songs;
  } 
    
    
    

  // Factory method to create a Song instance from a plain object
static fromDto(dto: any): Playlist {
  return new Playlist(
    dto.id,
    dto.title,
    dto.creator,
    dto.songs
  );
}

  }
  