#include<stdio.h>
#include<math.h>
#include<stdlib.h>
//int i,j,k,n;
	
int student_id[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
char heading[]={"Student_ID\tScore\tResponse_Time\n"};
	
//Stroop Application Response Time
float stroop_rt[]={22.99,25.56,21.14,31.07,16.52,22.84,29.55,18.68,23.14,25.3,21.1,32.92,25.81,21.2,18.42,48.51};

//Map and Match Response Time
float mm_rt[][16]={{2.142,5,1.428,1.764,2.307,1.5,2.5,1.485,1.875,11.25,3,2.307,1.578,1.875,2.142,3.333},{2.5,5,3,3.75,2.5,3.75,3.461,2.307,2.5,3,3.75,3,2.5,3,4.285,4.285},{2.5,3,1.764,7.5,2.142,2.5,5,2.5,2.727,2.5,4.285,2.727,3,3.333,3.333,4.285},{3.75,6.666,2.142,22.5,2.307,5,5,2.5,3,6.428,6,2,2.727,30,2.727,5},{5,6,6,22.5,3,15,6.428,3,10,3,15,3,10,10,4.285,15}};
	
//Card Game Response Time
float cg_rt[][16]={{9.084,14.244,17.197,7,7.523,6.857,9.241,4.545,9.996,8.13,3.703,6.743,11.13,23.016,7.53,18.352},{3.827,18.442,7.224,14.329,11.257,6.636,9.282,5.62,1.343,5.32,1.841,8.772,8.76,8.145,6.071,9.791},{6.504,18.517,14.644,11.236,4.761,8.651,9.506,2.986,11.912,6.515,1.363,7.638,9.91,11.049,9.544,16.508},{15.084,21.306,18.522,10.903,5.856,14.408,10.495,4.863,14.329,6.149,1.885,6.213,10.36,5.961,7.155,10.214}};

//Chess Objects Response Time
float co_rt[][16]={{7.802,13.851,23.974,14.814,19.57,22.653,20.84,5.141,12.705,13.325,19.31,9.098,12.5,14.08,6.921,27.554},{6.646,11.592,13.519,10.189,10.941,14.781,10.836,5.918,25.125,10.684,9.211,9.643,11.581,13.174,17.723,17.85},{7.431,16.008,22.121,9.911,13.826,19.294,18.252,30.901,16.892,20.868,14.818,5.588,19.519,8.75,13.539,17.611},{8.236,9.542,12.712,13.905,18.088,7.013,30.148,8.394,29.634,29.392,25.133,6.607,8.353,11.447,6.678,14.433}};

//Every column of Score and Response Time have been saved as a row

int find_mutual_information_mm()
{	
	double row_sum[20]={0}, pair_sum[20][16]={{0},{0}};
	double p_x[20][16]={{0.0},{0.0}};
	double p_y[20][16]={{0.0},{0.0}};
	double p_xy[20][16]={{0.0},{0.0}};
	double col_sum[20]={0};
	double mi[20][16]={{0.0},{0.0}};
	double mi_sum[20]={0};
	double loss[5]={0};
	
	//To find row sum
	int c=0;
	for(int i=0;i<5;i++)
	{
		for(int j=0;j<5;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				row_sum[c]+=mm_rt[i][k];
			}
			c++;
		}
	}
	//To find probability against row sum
	c=0;
	for(int i=0;i<5;i++)
	{
		for(int j=0;j<5;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				p_x[c][k]=mm_rt[i][k]/row_sum[c];
			}
			c++;
		}
	}

	//To find column sum and joint probability distribution
	c=0;
	for(int i=0;i<5;i++)
	{
		for(int j=0;j<5;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				pair_sum[c][k]=mm_rt[i][k]+mm_rt[j][k];
				col_sum[c]+=pair_sum[c][k];
				p_y[c][k]=mm_rt[i][k]/pair_sum[c][k];
			}
			c++;
		}
	}
	c=0;
	for(int i=0;i<5;i++)
	{
		for(int j=0;j<5;j++)
		{
			if(i==j) continue;
			printf("mi(%d,%d) ",i,j);
			for(int k=0;k<16;k++)
			{
				p_xy[c][k]=mm_rt[i][k]/(col_sum[c]+pair_sum[c][k]+row_sum[i]+row_sum[j]);
				mi[c][k]=p_xy[c][k]*logf(p_xy[c][k]/p_x[c][k]/p_y[c][k]);
				printf("%6.4lf ",mi[c][k]);
			}
			c++;
			printf("\n");
		}
		printf("\n");
	}
	
	//Construct look-up table for Mutual Information index selection
	int mi_index[5][5]={{0},{0}};
	int total_mi=0;
	c=0;
	for(int i=0;i<5;i++)
	{
		for(int j=0;j<5;j++)
		{
			if(i==j) continue;
			mi_index[i][j]=c;
			c++;
		}
		
	}
	//To find total MI (of all 16 persons)
	printf("MI Sum\n"); 
	for(int c=0;c<20;c++)
	{	
		for(int k=0;k<16;k++)
		{
			mi_sum[c]+=-mi[c][k];
			
		}
		printf("%lf\n",mi_sum[c]);
	}
	
	//To find total MI of all increasing levels (0,1),(1,2),(2,3) & (3,4)
	double grand_total=0;
	int select_levelx[4]={0,1,2,3};
	int select_levely[4]={1,2,3,4};
	double partial_total[5]={0};
	for(int i=0;i<4;i++)
	{
		grand_total+=mi_sum[mi_index[select_levelx[i]][select_levely[i]]];
	}
	printf("\nMI of all levels %lf\n",grand_total);

	//To find MI by excluding Level 0 (1,2),(2,3) & (3,4)
	int select_levelx0[]={1,2,3};
	int select_levely0[]={2,3,4};
	for(int i=0;i<3;i++)
	{
		partial_total[0]+=mi_sum[mi_index[select_levelx0[i]][select_levely0[i]]];
	}
	printf("MI (dropping Level 0) %lf\n",partial_total[0]);
	
	//To find MI by excluding Level 1 (0,2),(2,3) & (3,4) - (0,1) & (1,2)
	int select_levelx1[]={0,2,3};
	int select_levely1[]={2,3,4};
	int deselect_levelx1[]={0,1};
	int deselect_levely1[]={1,2};
	for(int i=0;i<3;i++)
	{
		partial_total[1]+=mi_sum[mi_index[select_levelx1[i]][select_levely1[i]]];
	}
	for(int i=0;i<2;i++)
	{
		partial_total[1]-=mi_sum[mi_index[deselect_levelx1[i]][deselect_levely1[i]]];
	}
	printf("MI (dropping Level 1) %lf\n",partial_total[1]);
	
	//To find MI by excluding Level 2 (0,1),(1,3) & (3,4) - (1,2) & (2,3)
	int select_levelx2[]={0,1,3};
	int select_levely2[]={1,3,4};
	int deselect_levelx2[]={0,2};
	int deselect_levely2[]={2,3};
	for(int i=0;i<3;i++)
	{
		partial_total[2]+=mi_sum[mi_index[select_levelx2[i]][select_levely2[i]]];
	}
	for(int i=0;i<2;i++)
	{
		partial_total[2]-=mi_sum[mi_index[deselect_levelx2[i]][deselect_levely2[i]]];
	}
	printf("MI (dropping Level 2) %lf\n",partial_total[2]);
	
	//To find MI by excluding Level 3 (0,1),(1,2) & (2,4) - (2,3) & (3,4)
	int select_levelx3[]={0,1,2};
	int select_levely3[]={1,2,4};
	int deselect_levelx3[]={2,3};
	int deselect_levely3[]={3,4};
	for(int i=0;i<3;i++)
	{
		partial_total[3]+=mi_sum[mi_index[select_levelx3[i]][select_levely3[i]]];
	}
	for(int i=0;i<2;i++)
	{
		partial_total[3]-=mi_sum[mi_index[deselect_levelx3[i]][deselect_levely3[i]]];
	}
	printf("MI (dropping Level 3) %lf\n",partial_total[3]);
	
	//To find MI by excluding Level 4 (0,1),(1,2) & (2,3)
	int select_levelx4[]={0,1,2};
	int select_levely4[]={1,2,3};
	for(int i=0;i<3;i++)
	{
		partial_total[4]+=mi_sum[mi_index[select_levelx4[i]][select_levely4[i]]];
	}
	printf("MI (dropping Level 4) %lf\n",partial_total[4]);
	
	//To find loss of information
	for(int i=0;i<5;i++)
	{
		loss[i]=grand_total-partial_total[i];
		printf("Loss (by dropping Level%d) %lf \n",i,loss[i]);
	}
	double min_loss=loss[1];
	for(int j=1;j<4;j++)
	{
		if(loss[j]<min_loss)	//Greedy logic to find minimum loss
			min_loss=loss[j];
	}
	printf("Minimum Loss (from intermediate levels) %lf \n",min_loss);
}

int find_mutual_information_cg()
{	
	double row_sum[12]={0}, pair_sum[12][16]={{0},{0}};
	double p_x[12][16]={{0.0},{0.0}};
	double p_y[12][16]={{0.0},{0.0}};
	double p_xy[12][16]={{0.0},{0.0}};
	double col_sum[12]={0};
	double mi[12][16]={{0.0},{0.0}};
	double mi_sum[12]={0};
	double loss[4]={0};
	
	//To find row sum
	int c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				row_sum[c]+=cg_rt[i][k];
			}
			c++;
		}
	}
	//To find probability against row sum
	//Row sum has been done separately because it is a 1-D array and p(x) is a 2-D array to comply with p(y)
	c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				p_x[c][k]=cg_rt[i][k]/row_sum[c];
			}
			c++;
		}
	}

	//To find column sum
	c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				pair_sum[c][k]=cg_rt[i][k]+cg_rt[j][k];
				col_sum[c]+=pair_sum[c][k];
				p_y[c][k]=cg_rt[i][k]/pair_sum[c][k];
			}
			c++;
		}
	}
	c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			printf("mi(%d,%d) ",i,j);
			for(int k=0;k<16;k++)
			{
				p_xy[c][k]=cg_rt[i][k]/(col_sum[c]+pair_sum[c][k]+row_sum[i]+row_sum[j]);
				mi[c][k]=p_xy[c][k]*logf(p_xy[c][k]/p_x[c][k]/p_y[c][k]);
				printf("%6.4lf ",mi[c][k]);
			}
			c++;
			printf("\n");
		}
		printf("\n");
	}
	
	//Construct look-up table for Mutual Information index selection
	int mi_index[4][4]={{0},{0}};
	int total_mi=0;
	c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			mi_index[i][j]=c;
//			printf("%d",mi_index[i][j]);
			c++;
		}
		
	}
	//To find total MI
	printf("MI Sum\n"); 
	for(int c=0;c<12;c++)
	{	
		for(int k=0;k<16;k++)
		{
			mi_sum[c]+=-mi[c][k];
			
		}
		printf("%lf\n",mi_sum[c]);
	}
	
	double grand_total=0;
	//To find total MI of all increasing levels (1,2),(2,3) & (3,4)
	int select_levelx[4]={1,2,3};
	int select_levely[4]={2,3,4};
	double partial_total[4]={0};
	for(int i=0;i<3;i++)
	{
		grand_total+=mi_sum[mi_index[select_levelx[i]][select_levely[i]]];
	}
	printf("\nMI of all levels %lf\n",grand_total);

	//To find MI by excluding Level 1 (2,3) & (3,4)
	int select_levelx0[]={2,3};
	int select_levely0[]={3,4};
	for(int i=0;i<2;i++)
	{
		partial_total[0]+=mi_sum[mi_index[select_levelx0[i]][select_levely0[i]]];
	}
	printf("MI (dropping Level 1) %lf\n",partial_total[0]);
	
	//To find MI by excluding Level 2 (1,3) & (3,4) - (1,2) & (2,3)
	int select_levelx1[]={1,3};
	int select_levely1[]={3,4};
	int deselect_levelx1[]={1,2};
	int deselect_levely1[]={2,3};
	for(int i=0;i<2;i++)
	{
		partial_total[1]+=mi_sum[mi_index[select_levelx1[i]][select_levely1[i]]];
	}
	for(int i=0;i<2;i++)
	{
		partial_total[1]-=mi_sum[mi_index[deselect_levelx1[i]][deselect_levely1[i]]];
	}
	printf("MI (dropping Level 2) %lf\n",partial_total[1]);
	
	//To find MI by excluding Level 3 (1,2) & (2,4) - (1,2) & (2,3)
	int select_levelx2[]={1,2};
	int select_levely2[]={2,4};
	int deselect_levelx2[]={1,2};
	int deselect_levely2[]={2,3};
	for(int i=0;i<2;i++)
	{
		partial_total[2]+=mi_sum[mi_index[select_levelx2[i]][select_levely2[i]]];
	}
	for(int i=0;i<2;i++)
	{
		partial_total[2]-=mi_sum[mi_index[deselect_levelx2[i]][deselect_levely2[i]]];
	}
	printf("MI (dropping Level 3) %lf\n",partial_total[2]);
	
	//To find MI by excluding Level 4 (1,2) & (2,3)
	int select_levelx4[]={1,2};
	int select_levely4[]={2,3};
	for(int i=0;i<2;i++)
	{
		partial_total[4]+=mi_sum[mi_index[select_levelx4[i]][select_levely4[i]]];
	}
	printf("MI (dropping Level 4) %lf\n",partial_total[4]);
	
	//To find loss of information
	for(int i=0;i<4;i++)
	{
		loss[i]=grand_total-partial_total[i];
		printf("Loss (by dropping Level%d) %lf \n",i,loss[i]);
	}
	double min_loss=loss[1];
	for(int j=1;j<3;j++)
	{
		if(loss[j]<min_loss)	//Greedy logic to find minimum loss
			min_loss=loss[j];
	}
	printf("Minimum Loss (from intermediate levels) %lf \n",min_loss);
}

int find_mutual_information_co()
{	
	double row_sum[12]={0}, pair_sum[12][16]={{0},{0}};
	double p_x[12][16]={{0.0},{0.0}};
	double p_y[12][16]={{0.0},{0.0}};
	double p_xy[12][16]={{0.0},{0.0}};
	double col_sum[12]={0};
	double mi[12][16]={{0.0},{0.0}};
	double mi_sum[12]={0};
	double loss[4]={0};
	
	//To find row sum
	int c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				row_sum[c]+=co_rt[i][k];
			}
			c++;
		}
	}
	//To find probability against row sum
	//Row sum has been done separately because it is a 1-D array and p(x) is a 2-D array to comply with p(y)
	c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				p_x[c][k]=co_rt[i][k]/row_sum[c];
			}
			c++;
		}
	}

	//To find column sum
	c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			for(int k=0;k<16;k++)
			{
				pair_sum[c][k]=co_rt[i][k]+co_rt[j][k];
				col_sum[c]+=pair_sum[c][k];
				p_y[c][k]=co_rt[i][k]/pair_sum[c][k];
			}
			c++;
		}
	}
	c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			printf("mi(%d,%d) ",i,j);
			for(int k=0;k<16;k++)
			{
				p_xy[c][k]=co_rt[i][k]/(col_sum[c]+pair_sum[c][k]+row_sum[i]+row_sum[j]);
				mi[c][k]=p_xy[c][k]*logf(p_xy[c][k]/p_x[c][k]/p_y[c][k]);
				printf("%6.4lf ",mi[c][k]);
			}
			c++;
			printf("\n");
		}
		printf("\n");
	}
	
	//Construct look-up table for Mutual Information index selection
	int mi_index[4][4]={{0},{0}};
	int total_mi=0;
	c=0;
	for(int i=0;i<4;i++)
	{
		for(int j=0;j<4;j++)
		{
			if(i==j) continue;
			mi_index[i][j]=c;
//			printf("%d",mi_index[i][j]);
			c++;
		}
		
	}
	//To find total MI
	printf("MI Sum\n"); 
	for(int c=0;c<12;c++)
	{	
		for(int k=0;k<16;k++)
		{
			mi_sum[c]+=-mi[c][k];
			
		}
		printf("%lf\n",mi_sum[c]);
	}
	
	double grand_total=0;
	//To find total MI of all increasing levels (1,2),(2,3) & (3,4)
	int select_levelx[4]={1,2,3};
	int select_levely[4]={2,3,4};
	double partial_total[4]={0};
	for(int i=0;i<3;i++)
	{
		grand_total+=mi_sum[mi_index[select_levelx[i]][select_levely[i]]];
	}
	printf("\nMI of all levels %lf\n",grand_total);

	//To find MI by excluding Level 1 (2,3) & (3,4)
	int select_levelx0[]={2,3};
	int select_levely0[]={3,4};
	for(int i=0;i<2;i++)
	{
		partial_total[0]+=mi_sum[mi_index[select_levelx0[i]][select_levely0[i]]];
	}
	printf("MI (dropping Level 1) %lf\n",partial_total[0]);
	
	//To find MI by excluding Level 2 (1,3) & (3,4) - (1,2) & (2,3)
	int select_levelx1[]={1,3};
	int select_levely1[]={3,4};
	int deselect_levelx1[]={1,2};
	int deselect_levely1[]={2,3};
	for(int i=0;i<2;i++)
	{
		partial_total[1]+=mi_sum[mi_index[select_levelx1[i]][select_levely1[i]]];
	}
	for(int i=0;i<2;i++)
	{
		partial_total[1]-=mi_sum[mi_index[deselect_levelx1[i]][deselect_levely1[i]]];
	}
	printf("MI (dropping Level 2) %lf\n",partial_total[1]);
	
	//To find MI by excluding Level 3 (1,2) & (2,4) - (1,2) & (2,3)
	int select_levelx2[]={1,2};
	int select_levely2[]={2,4};
	int deselect_levelx2[]={1,2};
	int deselect_levely2[]={2,3};
	for(int i=0;i<2;i++)
	{
		partial_total[2]+=mi_sum[mi_index[select_levelx2[i]][select_levely2[i]]];
	}
	for(int i=0;i<2;i++)
	{
		partial_total[2]-=mi_sum[mi_index[deselect_levelx2[i]][deselect_levely2[i]]];
	}
	printf("MI (dropping Level 3) %lf\n",partial_total[2]);
	
	//To find MI by excluding Level 4 (1,2) & (2,3)
	int select_levelx4[]={1,2};
	int select_levely4[]={2,3};
	for(int i=0;i<2;i++)
	{
		partial_total[4]+=mi_sum[mi_index[select_levelx4[i]][select_levely4[i]]];
	}
	printf("MI (dropping Level 4) %lf\n",partial_total[4]);
	
	//To find loss of information
	for(int i=0;i<4;i++)
	{
		loss[i]=grand_total-partial_total[i];
		printf("Loss (by dropping Level%d) %lf \n",i,loss[i]);
	}
	double min_loss=loss[1];
	for(int j=1;j<3;j++)
	{
		if(loss[j]<min_loss)	//Greedy logic to find minimum loss
			min_loss=loss[j];
	}
	printf("Minimum Loss (from intermediate levels) %lf \n",min_loss);
}
			
	
int main()
{
	printf("For Map and Match (Paired Cancellation) \n\n");
	find_mutual_information_mm();
	printf("For Card Game (Free Recall) \n\n");
	find_mutual_information_cg();
	printf("For Chess Game (Span Task) \n\n");
	find_mutual_information_co();
	return 0;
}
	
