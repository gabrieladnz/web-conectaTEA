import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendInviteModalComponent } from './send-invite-modal.component';

describe('SendInviteModalComponent', () => {
  let component: SendInviteModalComponent;
  let fixture: ComponentFixture<SendInviteModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SendInviteModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SendInviteModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
