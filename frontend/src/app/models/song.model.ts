import { User } from "./user.model";

export class Song {
    id?: string;             // identifiant unique (UUID ou DB id)
    title: string;          // titre de la chanson
    owner: string;         // album (optionnel)
    
    constructor(id: string, title: string, owner: string){
        this.id = id;
        this.title = title;
        this.owner = owner
    }

    // Factory method to create a Song instance from a plain object
  static fromDto(dto: any): Song {
    return new Song(
      dto.id,
      dto.title,
      dto.owner
    );
  }

}