// audio.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { response } from 'express';
import { tap } from 'rxjs';
import { Song } from '../models/song.model';


@Injectable({ providedIn: 'root' })
export class AudioService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  
  getSongs(){
    return this.http.get<Song[]>(`${this.baseUrl}/songs/me`);
  }
}
