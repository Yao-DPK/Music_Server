import { Song } from "./song.model";

// src/app/models/playlist.model.ts
export interface Playlist {
    id: string;
    title: string;
    description?: string;
    createdAt?: Date;
    songs: Song[];
  }
  