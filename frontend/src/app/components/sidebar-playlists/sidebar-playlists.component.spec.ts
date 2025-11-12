import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarPlaylistsComponent } from './sidebar-playlists.component';

describe('SidebarPlaylistsComponent', () => {
  let component: SidebarPlaylistsComponent;
  let fixture: ComponentFixture<SidebarPlaylistsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarPlaylistsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarPlaylistsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
