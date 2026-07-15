import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart } from 'chart.js/auto';
import { DashboardStats } from '../../model/dashboard.model';
import { DashboardService } from '../../services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements AfterViewInit {
  @ViewChild('accountsChart') accountsChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('operationsChart') operationsChartRef!: ElementRef<HTMLCanvasElement>;

  stats?: DashboardStats;
  errorMessage: string = '';

  constructor(private dashboardService: DashboardService) {}

  ngAfterViewInit(): void {
    this.dashboardService.getStats().subscribe({
      next: (stats) => {
        this.stats = stats;
        setTimeout(() => this.renderCharts(stats));
      },
      error: (err) => (this.errorMessage = err.error?.message || 'Erreur lors du chargement des statistiques')
    });
  }

  private renderCharts(stats: DashboardStats): void {
    new Chart(this.accountsChartRef.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Comptes courants', 'Comptes épargne'],
        datasets: [{
          data: [stats.totalCurrentAccounts, stats.totalSavingAccounts],
          backgroundColor: ['#0d6efd', '#198754']
        }]
      }
    });

    new Chart(this.operationsChartRef.nativeElement, {
      type: 'bar',
      data: {
        labels: ['Crédits', 'Débits'],
        datasets: [{
          label: 'Montant (DH)',
          data: [stats.totalCredits, stats.totalDebits],
          backgroundColor: ['#198754', '#ffc107']
        }]
      },
      options: {
        scales: { y: { beginAtZero: true } }
      }
    });
  }
}
