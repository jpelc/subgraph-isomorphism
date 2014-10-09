package pl.edu.agh.math;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import java.awt.*;

public class Chart {

    private XYSeries series1, series2, series3;
    private XYSeriesCollection dataset;
    double populationCounter = 0;
    /**
     *
     * @param title title of the chart
     * @param Xaxis Xaxis title
     * @param Yaxis Yaxis title
     */
    public Chart(String title,String Xaxis, String Yaxis){
        series1 = new XYSeries("Najlepsza wartość fitness");
        series2 = new XYSeries("Średnia wartość fitness");
        series3 = new XYSeries("Najgorsza wartość fitness");
        dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,//title
                Xaxis, // x-axis
                Yaxis, // y-axis
                dataset, // data
                PlotOrientation.VERTICAL, // orientation of the graph
                true, // show legend
                true, // show tooltips
                false);

        ChartFrame frame = new ChartFrame("Wykres funkcji fitness", chart);
        frame.setVisible(true);
        frame.setSize(960,540);
        XYPlot xyPlot = chart.getXYPlot();
        xyPlot.getRenderer().setSeriesStroke(1, new BasicStroke(5.0f));
        NumberAxis domainAxis = (NumberAxis) xyPlot.getDomainAxis();
        //domainAxis.setTickUnit(new NumberTickUnit(25));
        domainAxis.setVerticalTickLabels(true);
        domainAxis.setAutoRangeIncludesZero(true);

        StandardChartTheme theme = (StandardChartTheme)StandardChartTheme.createJFreeTheme();

        String fontName = "Lucida Sans";
        theme.setTitlePaint(Color.decode("#4572a7"));
        theme.setExtraLargeFont(new Font(fontName, Font.BOLD, 20)); //title
        theme.setLargeFont(new Font(fontName, Font.BOLD, 15)); //axis-title
        theme.setRegularFont(new Font(fontName, Font.PLAIN, 13)); //legenda
        theme.setRangeGridlinePaint(Color.decode("#C0C0C0"));
        theme.setPlotBackgroundPaint(Color.white);
        theme.setChartBackgroundPaint(Color.white);
        theme.setGridBandPaint(Color.red);
        theme.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
        theme.setBarPainter(new StandardBarPainter());
        theme.setAxisLabelPaint(Color.decode("#666666"));
        theme.apply(chart);
    }
    /**
     *
     * @param best Best fitness value
     * @param average Average fitness value
     * @param worst  Worst fitness value
     */
    public void updateChart(double best, double average, double worst){
        series1.add(populationCounter, best);
        series2.add(populationCounter,average);
        series3.add(populationCounter, worst);
        populationCounter+=1;
    }
}
