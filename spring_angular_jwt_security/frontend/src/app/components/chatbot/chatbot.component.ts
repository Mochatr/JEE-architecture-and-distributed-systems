import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatMessage } from '../../model/chat.model';
import { ChatService } from '../../services/chat.service';

@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.css'
})
export class ChatbotComponent {
  messages: ChatMessage[] = [];
  question: string = '';
  loading: boolean = false;

  constructor(private chatService: ChatService) {}

  ask(): void {
    const question = this.question.trim();
    if (!question) return;

    this.messages.push({ author: 'user', text: question });
    this.question = '';
    this.loading = true;

    this.chatService.ask(question).subscribe({
      next: (response) => {
        this.messages.push({ author: 'bot', text: response.answer });
        this.loading = false;
      },
      error: (err) => {
        this.messages.push({ author: 'bot', text: err.error?.message || 'Erreur lors de la communication avec le chatbot' });
        this.loading = false;
      }
    });
  }
}
