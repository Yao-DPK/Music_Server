import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Toast, ToastService } from '../../services/toast.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.scss'],
  imports: [CommonModule]
})
export class ToastComponent implements OnInit {
  toasts$: Observable<Toast[]> | undefined;

  constructor(private toastService: ToastService) {}

  ngOnInit() {
    this.toasts$ = this.toastService.toast$;
  }

  close(id: number) {
    this.toastService.remove(id);
  }
}
