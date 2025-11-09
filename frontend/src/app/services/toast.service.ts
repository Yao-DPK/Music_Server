import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Toast {
  id: number;
  message: string;
  type: 'success' | 'error' | 'info';
}

@Injectable({ providedIn: 'root' })
export class ToastService {
  private toasts: Toast[] = [];
  private toastSubject = new BehaviorSubject<Toast[]>([]);

  toast$ = this.toastSubject.asObservable();

  private idCounter = 0;

  show(message: string, type: 'success' | 'error' | 'info' = 'info', duration = 3000) {
    const toast: Toast = { id: ++this.idCounter, message, type };
    this.toasts.push(toast);
    this.toastSubject.next(this.toasts);

    setTimeout(() => this.remove(toast.id), duration);
  }

  remove(id: number) {
    this.toasts = this.toasts.filter(t => t.id !== id);
    this.toastSubject.next(this.toasts);
  }
}
