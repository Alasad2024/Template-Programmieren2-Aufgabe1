package org.htw.prog2.aufgabe1;
import org.apache.commons.cli.*;

public class HIVDiagnostics {

    /**
     * Parst die Kommandozeilenargumente. Gibt null zurück, falls:
     * <ul>
     *     <li>Ein Fehler beim Parsen aufgetreten ist (z.B. eins der erforderlichen Argumente nicht angegeben wurde)</li>
     *     <li>Bei -m, -d und -r nicht die gleiche Anzahl an Argumenten angegeben wurde</li>
     * </ul>
     * @param args Array mit Kommandozeilen-Argumenten
     * @return CommandLine-Objekt mit geparsten Optionen
     */

    public static CommandLine parseOptions(String[] args) {
        Options options = new Options();

        options.addOption(Option.builder("m")
                .longOpt("mutationfiles")
                .hasArgs()
                .required()
                .desc("Pfad zu Mutationsdatei")
                .build());

        options.addOption(Option.builder("d")
                .longOpt("drugnames")
                .hasArgs()
                .required()
                .desc("Name des Medikaments")
                .build());

        options.addOption(Option.builder("r")
                .longOpt("references")
                .hasArgs()
                .required()
                .desc("Pfad zu Referenzdatei")
                .build());

        options.addOption(Option.builder("p")
                .longOpt("patientseqs")
                .hasArg()
                .required()
                .desc("Pfad zu Patientensequenzen")
                .build());

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cli = parser.parse(options, args);

            int mutationCount = cli.getOptionValues("m").length;
            int drugCount = cli.getOptionValues("d").length;
            int referenceCount = cli.getOptionValues("r").length;

            if (mutationCount != drugCount || mutationCount != referenceCount) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("HIVDiagnostics", options);
                return null;
            }

            return cli;
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("HIVDiagnostics", options);
            return null;
        }
    }

    public static void main(String[] args) {
    }
}
