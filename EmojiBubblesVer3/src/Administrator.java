import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrator extends ApplicationFrame {

    /**
     * Constructor to initialize the frame and add the pie chart.
     * 
     * @param title the frame title.
     */
    public Administrator(String title) {
        super(title);
        final DefaultPieDataset dataset = createDataset();  // Create the dataset based on SQL data
        final JFreeChart chart = createChart(dataset);       // Create the pie chart from the dataset

        // Add the chart to a panel and set it as the content pane
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    /**
     * Creates the dataset for the pie chart using data from the database.
     * 
     * @return the dataset.
     */
    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Fetch the data from the database
        ResultSet rs = SQLQuery.fetchStatisticData();  // Make sure the fetch method fetches data from the last month

        if (rs != null) {
            try {
                int שמחהCount = 0;
                int רגעCount = 0;
                int עצבCount = 0;
                int חזקהCount = 0;
                int פחדCount = 0;
                int כעסCount = 0;

                // Iterate through the ResultSet and accumulate the counts for each emotion
                while (rs.next()) {
                    שמחהCount += rs.getInt("שמחה");
                    רגעCount += rs.getInt("רגע");
                    עצבCount += rs.getInt("עצב");
                    חזקהCount += rs.getInt("חזקה");
                    פחדCount += rs.getInt("פחד");
                    כעסCount += rs.getInt("כעס");
                }

                // Add the data to the dataset
                dataset.setValue("שמחה (Happiness)", (double) שמחהCount);
                dataset.setValue("רגע (Calmness)", (double) רגעCount);
                dataset.setValue("עצב (Sadness)", (double) עצבCount);
                dataset.setValue("חזקה (Strength)", (double) חזקהCount);
                dataset.setValue("פחד (Fear)", (double) פחדCount);
                dataset.setValue("כעס (Anger)", (double) כעסCount);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataset;
    }

    /**
     * Creates the pie chart using the dataset.
     * 
     * @param dataset the dataset to be used in the chart.
     * @return the created chart.
     */
    private JFreeChart createChart(DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Emotion Distribution",  // Chart title
                dataset,                 // Dataset
                true,                    // Include legend
                true,                    // Include tooltips
                false                    // Do not include URLs
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setNoDataMessage("No data available");

        // Customize the appearance of the chart
        plot.setSectionPaint("שמחה (Happiness)", new Color(255, 165, 0));  // Orange
		plot.setSectionPaint("רגע (Calmness)", new Color(255, 192, 203));    // Pink
        plot.setSectionPaint("עצב (Sadness)", new Color(169, 169, 169));   // Gray
        plot.setSectionPaint("חזקה (Strength)", new Color(0, 128, 0));    // Green
        plot.setSectionPaint("פחד (Fear)", new Color(128, 0, 128));       // Purple
        plot.setSectionPaint("כעס (Anger)", new Color(255, 0, 0));        // Red

        plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0}: {1}"));
        plot.setLabelLinkStyle(org.jfree.chart.plot.PieLabelLinkStyle.STANDARD);

        return chart;
    }

    /**
     * Main method to run the application.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
    	SQLQuery.connect("root", "123456");
        Administrator demo = new Administrator("Emotion Statistics Pie Chart");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        SQLQuery.disconnect();
    }
}
