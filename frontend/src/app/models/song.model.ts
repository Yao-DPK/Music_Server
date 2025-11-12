import { User } from "./user.model";

export interface Song {
    id: string;             // identifiant unique (UUID ou DB id)
    title: string;          // titre de la chanson
    album?: string;         // album (optionnel)
    coverUrl?: string;      // URL de la pochette
    audioUrl: string;       // URL ou chemin du fichier audio
    duration?: number;      // durée en secondes (optionnel)
    addedAt?: Date;         // date d’ajout (optionnel)
    user: User;
}