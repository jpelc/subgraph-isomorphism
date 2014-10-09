package pl.edu.agh.graph.isomorphism;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.math3.genetics.*;
import pl.edu.agh.graph.Edge;
import pl.edu.agh.graph.GraphGenerator;
import pl.edu.agh.graph.Vertex;
import pl.edu.agh.graph.isomorphism.algorithm.crossover.PartiallyMapedCrossover;
import pl.edu.agh.graph.isomorphism.algorithm.mutation.InsertMutation;
import pl.edu.agh.graph.isomorphism.algorithm.mutation.InversionMutation;
import pl.edu.agh.graph.isomorphism.algorithm.mutation.ScrambleMutation;
import pl.edu.agh.graph.isomorphism.algorithm.mutation.SwapMutation;
import pl.edu.agh.graph.isomorphism.algorithm.selection.RouletteSelection;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Viewer {

    private GraphGenerator generator;
    private Thread algorithmThread;

    // Variables declaration - do not modify
    private JFrame window;
    private VisualizationViewer<Vertex, Edge> vvA;
    private VisualizationViewer<Vertex, Edge> vvB;
    private javax.swing.JLabel algorithmLabel;
    private javax.swing.JComboBox<String> biggerGraphSizeBox;
    private javax.swing.JCheckBox chartCheckBox;
    private javax.swing.JLabel colonLabel;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JTextField crossoverProbabilityEditText;
    private javax.swing.JLabel crossoverProbabilityLabel;
    private javax.swing.JComboBox<String> crossoverTypeBox;
    private javax.swing.JLabel crossoverTypeLabel;
    private javax.swing.JComboBox<String> edgesFFRatioBox;
    private javax.swing.JLabel fitnessFunctionsRatioLabel;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel graphSizeLabel;
    private javax.swing.JLabel graphSizeLabelBigger;
    private javax.swing.JLabel graphSizeLabelSmaller;
    private javax.swing.JComboBox<String> graphsLayoutBox;
    private javax.swing.JLabel graphsLayoutLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JTextField mutationProbabilityEditText;
    private javax.swing.JLabel mutationProbabilityLabel;
    private javax.swing.JComboBox<String> mutationTypeBox;
    private javax.swing.JLabel mutationTypeLabel;
    private javax.swing.JTextField populationSizeEditText;
    private javax.swing.JLabel populationSizeLabel;
    private javax.swing.JLabel probabilityLabel;
    private javax.swing.JButton runButton;
    private javax.swing.JComboBox<String> selectionTypeBox;
    private javax.swing.JLabel selectionTypeLabel;
    private javax.swing.JSeparator separator1;
    private javax.swing.JComboBox<String> smallerGraphSizeBox;
    private javax.swing.JComboBox<String> stoppingConditionBox;
    private javax.swing.JLabel stoppingConditionLabel;
    private javax.swing.JTextField stoppingConditionOptionsEditText;
    private javax.swing.JComboBox<String> typeOfGraphBox;
    private javax.swing.JLabel typeOfGraphLabel;
    private javax.swing.JComboBox<String> vertexFFRatioBox;
    // End of variables declaration

    //------------------------------------------------------------------------------------------------------------------

    public Viewer() {
        window = new JFrame("Problem izmorfizmu podgrafu");
        generator = new GraphGenerator();
        algorithmThread = null;
        init();

    }

    private void init() {
        setLookAndFeel();
        //creating components
        controlPanel = new javax.swing.JPanel();
        graphSizeLabel = new javax.swing.JLabel();
        graphSizeLabelSmaller = new javax.swing.JLabel();
        graphSizeLabelBigger = new javax.swing.JLabel();
        smallerGraphSizeBox = new javax.swing.JComboBox<String>();
        biggerGraphSizeBox = new javax.swing.JComboBox<String>();
        separator1 = new javax.swing.JSeparator();
        generateButton = new javax.swing.JButton();
        graphsLayoutLabel = new javax.swing.JLabel();
        graphsLayoutBox = new javax.swing.JComboBox<String>();
        algorithmLabel = new javax.swing.JLabel();
        populationSizeLabel = new javax.swing.JLabel();
        populationSizeEditText = new javax.swing.JTextField();
        stoppingConditionLabel = new javax.swing.JLabel();
        stoppingConditionOptionsEditText = new javax.swing.JTextField();
        runButton = new javax.swing.JButton();
        chartCheckBox = new javax.swing.JCheckBox();
        typeOfGraphLabel = new javax.swing.JLabel();
        typeOfGraphBox = new javax.swing.JComboBox<String>();
        fitnessFunctionsRatioLabel = new javax.swing.JLabel();
        edgesFFRatioBox = new javax.swing.JComboBox<String>();
        colonLabel = new javax.swing.JLabel();
        vertexFFRatioBox = new javax.swing.JComboBox<String>();
        probabilityLabel = new javax.swing.JLabel();
        crossoverProbabilityLabel = new javax.swing.JLabel();
        mutationProbabilityLabel = new javax.swing.JLabel();
        crossoverProbabilityEditText = new javax.swing.JTextField();
        mutationProbabilityEditText = new javax.swing.JTextField();
        stoppingConditionBox = new javax.swing.JComboBox<String>();
        crossoverTypeLabel = new javax.swing.JLabel();
        crossoverTypeBox = new javax.swing.JComboBox<String>();
        mutationTypeLabel = new javax.swing.JLabel();
        mutationTypeBox = new javax.swing.JComboBox<String>();
        selectionTypeLabel = new javax.swing.JLabel();
        selectionTypeBox = new javax.swing.JComboBox<String>();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();

        window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        window.setMinimumSize(new java.awt.Dimension(800, 600));
        window.setName("window"); // NOI18N
        window.setPreferredSize(new java.awt.Dimension(800, 600));

        controlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Konfiguracja"));
        controlPanel.setPreferredSize(new java.awt.Dimension(250, 600));
        
        graphSizeLabel.setText("Liczba wierzchołków w grafach");

        graphSizeLabelSmaller.setText("Mniejszy:");

        graphSizeLabelBigger.setText("Większy:");

        smallerGraphSizeBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75",
                "80", "85", "90", "95", "100", "110", "120", "130", "140", "150", "160", "170", "180", "190", "200",
                "220", "240", "260", "280", "300", "320", "340", "360", "380", "400", "420", "440", "460", "480", "500",
                "520", "540", "560", "580", "600", "620", "640", "660", "680", "700", "720", "740", "760", "780", "800",
                "820", "840", "860", "880", "900", "920", "940", "960", "980", "1000"}));

        biggerGraphSizeBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75",
                "80", "85", "90", "95", "100", "110", "120", "130", "140", "150", "160", "170", "180", "190", "200",
                "220", "240", "260", "280", "300", "320", "340", "360", "380", "400", "420", "440", "460", "480", "500",
                "520", "540", "560", "580", "600", "620", "640", "660", "680", "700", "720", "740", "760", "780", "800",
                "820", "840", "860", "880", "900", "920", "940", "960", "980", "1000"}));

        generateButton.setText("Generuj");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        graphsLayoutLabel.setText("Wygląd grafów");

        graphsLayoutBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"ISOMLayout", "KKLayout", "FRLayout", "CircleLayout", "SpringLayout"}));
        graphsLayoutBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphsLayoutBoxActionPerformed(evt);
            }
        });

        algorithmLabel.setText("Algorytm");

        populationSizeLabel.setText("Rozmiar populacji:");

        populationSizeEditText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        populationSizeEditText.setText("100");

        stoppingConditionLabel.setText("Warunek stopu:");

        stoppingConditionOptionsEditText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        stoppingConditionOptionsEditText.setText("500");

        runButton.setText("Start");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        chartCheckBox.setSelected(true);
        chartCheckBox.setText("Wykres");

        typeOfGraphLabel.setText("Rodzaj grafu:");


        typeOfGraphBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "skierowany", "nieskierowany" }));
        typeOfGraphBox.setSelectedIndex(1);

        fitnessFunctionsRatioLabel.setText("Waga funkcji (krawędzie : stopnie):");

        edgesFFRatioBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] {"0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0" }));
        edgesFFRatioBox.setSelectedIndex(8);
        edgesFFRatioBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edgesFFRatioBoxActionPerformed(evt);
            }
        });

        colonLabel.setText(":");

        vertexFFRatioBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] {"0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0" }));
        vertexFFRatioBox.setSelectedIndex(2);
        vertexFFRatioBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vertexFFRatioBoxActionPerformed(evt);
            }
        });


        probabilityLabel.setText("Prawdopodobieństwo [%]:");

        crossoverProbabilityLabel.setText("Krzyżowanie:");

        mutationProbabilityLabel.setText("Mutacja:");

        crossoverProbabilityEditText.setText("60");

        mutationProbabilityEditText.setText("20");

        stoppingConditionBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Ilość iteracji", "Czas działania" }));

        crossoverTypeLabel.setText("Kryżowanie:");

        crossoverTypeBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Cykliczne", "Order", "PMX" }));

        mutationTypeLabel.setText("Mutacja:");

        mutationTypeBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Swap", "Inversion", "Scramble", "Insert" }));

        selectionTypeLabel.setText("Selekcja:");

        selectionTypeBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Turniejowa", "Ruletkowa" }));

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
                controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(controlPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(separator1)
                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(graphSizeLabel)
                                                                .addGroup(controlPanelLayout.createSequentialGroup()
                                                                        .addGap(10, 10, 10)
                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(graphSizeLabelSmaller, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(graphSizeLabelBigger, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addGap(9, 9, 9)
                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(smallerGraphSizeBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(biggerGraphSizeBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(generateButton))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                                                                        .addComponent(graphsLayoutLabel)
                                                                        .addGap(26, 26, 26)
                                                                        .addComponent(graphsLayoutBox, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(controlPanelLayout.createSequentialGroup()
                                                                        .addComponent(typeOfGraphLabel)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(typeOfGraphBox, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(algorithmLabel)
                                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(probabilityLabel)
                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addGroup(controlPanelLayout.createSequentialGroup()
                                                                                        .addGap(10, 10, 10)
                                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(crossoverProbabilityLabel)
                                                                                                .addComponent(mutationProbabilityLabel))
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                .addComponent(crossoverProbabilityEditText, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                                                                                .addComponent(mutationProbabilityEditText)))
                                                                                .addGroup(controlPanelLayout.createSequentialGroup()
                                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                                                                                                                .addComponent(populationSizeLabel)
                                                                                                                .addGap(18, 18, 18))
                                                                                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                                                                                .addComponent(crossoverTypeLabel)
                                                                                                                .addGap(46, 46, 46)))
                                                                                                .addGroup(controlPanelLayout.createSequentialGroup()
                                                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(stoppingConditionLabel)
                                                                                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                        .addComponent(mutationTypeLabel)
                                                                                                                        .addComponent(selectionTypeLabel)))
                                                                                                        .addGap(28, 28, 28)))
                                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                .addComponent(stoppingConditionOptionsEditText)
                                                                                                .addComponent(populationSizeEditText)
                                                                                                .addComponent(crossoverTypeBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(selectionTypeBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(mutationTypeBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(stoppingConditionBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(fitnessFunctionsRatioLabel)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                                                                                        .addComponent(edgesFFRatioBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                        .addComponent(colonLabel)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                        .addComponent(vertexFFRatioBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(16, 16, 16))))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(chartCheckBox)
                                                .addGap(12, 12, 12)
                                                .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
                controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(controlPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(graphSizeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(graphSizeLabelSmaller)
                                                        .addComponent(smallerGraphSizeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(graphSizeLabelBigger)
                                                        .addComponent(biggerGraphSizeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(generateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(typeOfGraphLabel)
                                        .addComponent(typeOfGraphBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(graphsLayoutLabel)
                                        .addComponent(graphsLayoutBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(algorithmLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(populationSizeLabel)
                                        .addComponent(populationSizeEditText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(crossoverTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(crossoverTypeLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mutationTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mutationTypeLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(selectionTypeLabel)
                                        .addComponent(selectionTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(stoppingConditionLabel)
                                        .addComponent(stoppingConditionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stoppingConditionOptionsEditText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fitnessFunctionsRatioLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(edgesFFRatioBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(colonLabel)
                                        .addComponent(vertexFFRatioBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(probabilityLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(crossoverProbabilityLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(crossoverProbabilityEditText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mutationProbabilityEditText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mutationProbabilityLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(runButton)
                                        .addComponent(chartCheckBox))
                                .addContainerGap())
        );


        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 100));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(23, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(183, 100));

        logTextArea.setColumns(20);
        logTextArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        logTextArea.setLineWrap(true);
        logTextArea.setRows(5);
        logTextArea.setTabSize(4);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setMaximumSize(new java.awt.Dimension(1920, 100));
        logTextArea.setMinimumSize(new java.awt.Dimension(500, 100));
        jScrollPane1.setViewportView(logTextArea);
        DefaultCaret caret = (DefaultCaret) logTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //graphs initialization
        DirectedSparseGraph<Vertex, Edge> g1 = new DirectedSparseGraph<Vertex, Edge>();
        DirectedSparseGraph<Vertex, Edge> g2 = new DirectedSparseGraph<Vertex, Edge>();
        g1.addVertex(new Vertex("Proszę", 1));
        g1.addVertex(new Vertex("wygenerować", 2));
        g2.addVertex(new Vertex("własne", 1));
        g2.addVertex(new Vertex("grafy!", 2));
        vvA = new VisualizationViewer<Vertex, Edge>(new ISOMLayout<Vertex, Edge>(g1));
        vvB = new VisualizationViewer<Vertex, Edge>(new ISOMLayout<Vertex, Edge>(g2));
        vvA.setBorder(new TitledBorder("Graf A - mniejszy"));
        vvB.setBorder(new TitledBorder("Graf B - większy"));
        DefaultModalGraphMouse<Object, Object> gm = new DefaultModalGraphMouse<Object, Object>();
        vvA.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Vertex>());
        vvB.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Vertex>());
        
        //moj kod
        
        
        
        
        //koniec
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vvA.setGraphMouse(gm);
        vvB.setGraphMouse(gm);
        vvA.setPreferredSize(new Dimension(450, 600));
        vvB.setPreferredSize(new Dimension(450, 600));


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(window.getContentPane());
        window.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(vvA, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(vvB, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(vvB, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                                                        .addComponent(vvA, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        window.pack();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int smaller = Integer.parseInt((String)smallerGraphSizeBox.getSelectedItem());
        int bigger = Integer.parseInt((String)biggerGraphSizeBox.getSelectedItem());
        int typeOfGraph = typeOfGraphBox.getSelectedIndex();
        String layout = (String) graphsLayoutBox.getSelectedItem();
        int graphsLayout = graphsLayoutBox.getSelectedIndex();
        if(smaller <= bigger) {
            generateGraphs(smaller, bigger, typeOfGraph, layout);
            setGraphsModel(graphsLayout);
        } else {
            JOptionPane.showMessageDialog(window, "Większy graf musi mieć "
                    + "co najmniej tyle wierzchołków, co mniejszy!", "Błąd",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(!generator.isGenerated()) {
            JOptionPane.showMessageDialog(window, "Brak grafów!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        logTextArea.setText("");
        int size, stoppingConditionOptions;
        double crossProb, mutProb, f1weight, f2weight;
        try {
            size = Integer.parseInt(populationSizeEditText.getText());
            stoppingConditionOptions = Integer.parseInt(stoppingConditionOptionsEditText.getText());
            crossProb = Double.parseDouble(crossoverProbabilityEditText.getText());
            mutProb = Double.parseDouble(mutationProbabilityEditText.getText());
            f1weight = Double.parseDouble((String)edgesFFRatioBox.getSelectedItem());
            f2weight = Double.parseDouble((String)vertexFFRatioBox.getSelectedItem());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(window, "Nieodpowiedni format danych wejściowych!", "Błąd",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(size < 2 || stoppingConditionOptions < 1 || crossProb < 0 || crossProb > 100 || mutProb < 0 || mutProb > 100) {
            JOptionPane.showMessageDialog(window, "Nieodpowiednie wartości parametrów wejściowych!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int co = crossoverTypeBox.getSelectedIndex();
        CrossoverPolicy crossoverPolicy = co == 0 ? new CycleCrossover<Integer>() : (co == 1 ? new OrderedCrossover<Integer>() :
                new PartiallyMapedCrossover<Integer>());

        int mt = mutationTypeBox.getSelectedIndex();
        MutationPolicy mutationPolicy = mt == 0 ? new SwapMutation() : (mt == 1 ? new InversionMutation() : (mt == 2 ?
                new ScrambleMutation() : new InsertMutation()));
        
        SelectionPolicy selectionPolicy = selectionTypeBox.getSelectedIndex() == 0 ? new TournamentSelection(size/8) :
                new RouletteSelection();
        StoppingCondition stoppingCondition = stoppingConditionBox.getSelectedIndex() == 0 ?
                new FixedGenerationCount(stoppingConditionOptions) : new FixedElapsedTime(stoppingConditionOptions);

        boolean chartDrawing = chartCheckBox.isSelected();
        setButtonsState(false);
        algorithmThread = new Thread(new AlgorithmManager(crossoverPolicy, crossProb/100, mutationPolicy,
                mutProb/100, selectionPolicy, stoppingCondition, size, f1weight, f2weight,
                chartDrawing, this));
        algorithmThread.start();
    }

    /**
     * Niszczenie wątku algorytmu w przypadku znalezienia podgrafu przed wystąpieniem 
     * warunku stopu. Na pewno nie powinno się tak robić, ale w ten sposób jest najszybciej.
     * Podczas niszczenia wątku sypie wyjątkiem, ale nie wpływa to na pracę programu :D
     */
    public void killThread() {
        setButtonsState(true);
        algorithmThread.destroy();
    }

    private void graphsLayoutBoxActionPerformed(java.awt.event.ActionEvent evt) {
        int index = graphsLayoutBox.getSelectedIndex();
        setGraphsModel(index);
        logTextArea.setText(logTextArea.getText() + "Zmieniono layout na " + graphsLayoutBox.getSelectedItem() + "\n\n");
    }

    private void edgesFFRatioBoxActionPerformed(java.awt.event.ActionEvent evt) {
        vertexFFRatioBox.setSelectedIndex(10 - edgesFFRatioBox.getSelectedIndex());
    }

    private void vertexFFRatioBoxActionPerformed(java.awt.event.ActionEvent evt) {
        edgesFFRatioBox.setSelectedIndex(10 - vertexFFRatioBox.getSelectedIndex());
    }

    private void generateGraphs(int smaller, int bigger, int type, String layout) {
        //type: 0 - directed; 1 undirected
        if(type == 0)
            generator.generatePairOfDirectedGraphs(smaller, bigger);
        else
            generator.generatePairOfUndirectedGraphs(smaller, bigger);
        String t = (type == 0) ? "skierowany" : "nieskierowany";
        logTextArea.setText(logTextArea.getText() + "Wygenerowano graf " + t +" (" + layout + "):\n" + "Graf A: |V| = " + smaller + "   |E| = " +
                GraphGenerator.getSmaller().getEdgeCount() + "   |   Graf B: |V| = " + bigger + "   |E| = " +
                GraphGenerator.getBigger().getEdgeCount() + "\n\n");
    }

    private void setGraphsModel(int index) {
        AbstractLayout<Vertex, Edge> layoutSmaller = null;
        AbstractLayout<Vertex, Edge> layoutBigger = null;
        switch(index) {
            case 0:
                layoutSmaller = new ISOMLayout<Vertex, Edge>(GraphGenerator.getSmaller());
                layoutBigger = new ISOMLayout<Vertex, Edge>(GraphGenerator.getBigger());
                break;
            case 1:
                layoutSmaller = new KKLayout<Vertex, Edge>(GraphGenerator.getSmaller());
                layoutBigger = new KKLayout<Vertex, Edge>(GraphGenerator.getBigger());
                break;
            case 2:
                layoutSmaller = new FRLayout<Vertex, Edge>(GraphGenerator.getSmaller());
                layoutBigger = new FRLayout<Vertex, Edge>(GraphGenerator.getBigger());
                break;
            case 3:
                layoutSmaller = new CircleLayout<Vertex, Edge>(GraphGenerator.getSmaller());
                layoutBigger = new CircleLayout<Vertex, Edge>(GraphGenerator.getBigger());
                break;
            case 4:
                layoutSmaller = new SpringLayout<Vertex, Edge>(GraphGenerator.getSmaller());
                layoutBigger = new SpringLayout<Vertex, Edge>(GraphGenerator.getBigger());
                break;
            case 5:
                layoutSmaller = new SpringLayout2<Vertex, Edge>(GraphGenerator.getSmaller());
                layoutBigger = new SpringLayout2<Vertex, Edge>(GraphGenerator.getBigger());
                break;
        }
        vvA.setGraphLayout(layoutSmaller);
        vvB.setGraphLayout(layoutBigger);
    }

    private void show() {
        window.setVisible(true);
    }

    /**
     * Dodanie informacji do logu
     * @param s informacja do dodania
     */
    public void updateLog(String s) {
        logTextArea.setText(logTextArea.getText() + s + "\n");
    }

    /**
     * Kolorowanie wierzchołków grafu
     * @param isoVertices tablica wierzchołków do pokolorowania
     */
    public void colorSubGraph(int[] isoVertices){
        /*

        */
        logTextArea.setCaretPosition(logTextArea.getLineCount()*logTextArea.getColumns());
        final LinkedList<Vertex> toColor = new LinkedList<Vertex>();
        ArrayList<Vertex> biggerGraphVertices = GraphGenerator.getBiggerVertex();
        for(int i = 0; i < isoVertices.length; i++){
            for(Vertex v: biggerGraphVertices){
                if(v.getId() == isoVertices[i])
                    toColor.add(v);
            }
        }
        Transformer<Vertex, Paint> vertexPaint =
                new Transformer<Vertex, Paint>() {
                    @Override
                    public Paint transform(Vertex v) {
                        if(toColor.contains(v))
                            return Color.CYAN;
                        else return Color.RED;
                    }
                };
        vvB.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        window.repaint();
    }

    public void setButtonsState(boolean on) {
        runButton.setEnabled(on);
        generateButton.setEnabled(on);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Viewer().show();
            }
        });
    }
}