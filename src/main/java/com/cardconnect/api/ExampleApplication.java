package com.cardconnect.api;

import com.cardconnect.api.service.ExampleService;
import com.cardconnect.api.service.RestClientService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class ExampleApplication {
    private static Options options;
    private static boolean argMode = true;
    private static Scanner scanner;
    private static char CREDENTIALS = 'c';
    private static char DATA = 'd';
    private static char EXAMPLE = 's';
    private static char ENDPOINT = 'e';
    private static char URL = 'u';
    private static char HELP = 'h';
    private static RestClientService restClientService;

    static  {
        options = new Options();
        options.addOption("h", "help", false, "show help");
        options.addOption("c","credentials",true,"credentials for transaction in '<username>:<password>' format");
        options.addOption("d","data",true,"transaction's body data");
        options.addOption("s", "sample", false, "show sample request/response payload of transaction <type>");
        options.addOption("u","url",true,"api url");
        options.addOption("e","endpoint",true,"api endpoint");
    }

    public static void main(String[] args) {
        if(args.length == 0){
            argMode = false;
        }

        if(argMode){
            argMode(args);
        }else {
            menuMode();
        }
    }

    private static void argMode(String [] args){
        CommandLine commandLine = generateCommandLine(options,args);
        if(!commandLine.hasOption(HELP)) {
        if(commandLine.hasOption(EXAMPLE)){
            if(commandLine.hasOption(ENDPOINT)) {
                int option = getOption(commandLine.getOptionValue(ENDPOINT));
                if(option > 0) {
                    ExampleService.showExample(option);
                }
            }else {
                err.println("-e option is required to get right sample payload");
                printHelp(options);
            }
        }else {

                if (!commandLine.hasOption(CREDENTIALS) || !commandLine.hasOption(URL) || !commandLine.hasOption(DATA) || !commandLine.hasOption(ENDPOINT)) {
                    err.println("ERROR: All four args required in order to run transaction: -c, -u, -d, -e");
                    printHelp(options);
                } else {
                    int option = getOption(commandLine.getOptionValue(ENDPOINT));
                    if (option > 0) {
                        restClientService = new RestClientService(commandLine.getOptionValue(URL), commandLine.getOptionValue(CREDENTIALS));
                        restClientService.runTransaction(option, commandLine.getOptionValue(DATA));
                    }
                }
            }
        }else {
            printHelp(options);
        }
    }

    private static void menuMode(){
        scanner = new Scanner(System.in);
        out.println("====================================");
        out.println("CardConnect Gateway REST API Client");
        out.println("=====================================");
        while (true){
            Long option = printMainMenu();
            if(option != -1) {
                if (option == 0) {
                    System.exit(0);
                }

                if(option < 0 || option > 3){
                    out.println("Invalid input '" + option + "' please re-enter!");
                    continue;
                }
                String url=null, apiCredentials=null;
                while (true) {
                    int option2 = printEndpointMenu(option);
                    if (option2 <= 14) {
                        if(option == 1) {
                            ExampleService.showExample(option2);
                        }else {
                            if(restClientService == null || isEmpty(url) || isEmpty(apiCredentials)) {
                                if(isEmpty(url)) {
                                    url = promptForInput("Enter API URL");
                                    if (isEmpty(url)) {
                                        throw new IllegalArgumentException("API URL cannot be empty");
                                    }
                                }

                                if(isEmpty(apiCredentials)) {
                                    apiCredentials = promptForInput("Enter API credentials");
                                    if (isEmpty(apiCredentials) || !apiCredentials.contains(":")) {
                                        throw new IllegalArgumentException("API credentials cannot be empty or must be in <username>:<password> format");
                                    }
                                }
                                restClientService = new RestClientService(url, apiCredentials);
                            }
                            String data = promptForInput("Enter API Data, supply json payload or comma separated url parameters");
                            restClientService.runTransaction(option2,data);
                        }
                    }else {
                        out.println("Invalid input '" + option2 + "' please re-enter!");
                        continue;
                    }

                    if (option2 == 0) {
                        break;
                    }
                }
            }
        }
    }

    private static Long printMainMenu(){
        out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        out.println("~~~>     Main Menu");
        out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        out.println("1. Show Example");
        out.println("2. Run Transaction");
        out.println("0. Exit");
        String input = trimToNull(promptForInput("Enter option # here"));
        try {
            return Long.parseLong(input);
        }catch (Exception e){
            out.println("Invalid input '" + input + "' please re-enter!");
            return -1L;
        }
    }

    private static int printEndpointMenu(Long option){
        String prefix;
        if(option == 1){
            prefix = "Show Example ";
        }else {
            prefix = " Run Transaction ";
        }
        out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        out.println("~~~>     " + prefix + "Menu");
        out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        out.println("1. Auth");
        out.println("2. Capture");
        out.println("3. Void");
        out.println("4. Refund");
        out.println("5. Inquire");
        out.println("6. Settle status");
        out.println("7. Funding");
        out.println("8. Read Profile");
        out.println("9. Create/Update Profile");
        out.println("10. Delete Profile");
        out.println("11. Signature");
        out.println("12. Open batch");
        out.println("13. Close batch");
        out.println("14. Bin");
        out.println("0. Go Back to Main Menu");
        out.println("----------------------------------");
        String input = trimToNull(promptForInput(prefix + "-> Enter option # here"));
        try {
            return Integer.parseInt(input);
        }catch (Exception e){
            out.println("Invalid input '" + input + "' please re-enter!");
            return -1;
        }
    }

    private static String promptForInput(String prompt) {
        out.print(prompt + ": ");
        return scanner.nextLine();
    }

    /**
     * Print help.
     *
     * @param options the options
     */
    private static void printHelp(final Options options)
    {
        final HelpFormatter formatter = new HelpFormatter();
        final String syntax = "java -jar java-cardconnect-api-v2-onejar.jar -<op> <option-value>";
        final String usageHeader = "Options of Using CardConnect API";
        formatter.printHelp(120,syntax, usageHeader, options,null);
    }

    /**
     * Generate command line command line.
     *
     * @param options              the options
     * @param commandLineArguments the command line arguments
     * @return the command line
     */
    private static CommandLine generateCommandLine(final Options options, final String[] commandLineArguments) {
        final CommandLineParser cmdLineParser = new DefaultParser();
        CommandLine commandLine = null;
        try
        {
            commandLine = cmdLineParser.parse(options, commandLineArguments);
        } catch (ParseException parseException) {
            out.println(
                    "ERROR: Unable to parse command-line arguments "
                            + Arrays.toString(commandLineArguments) + " due to: "
                            + parseException);
            printHelp(options);
            System.exit(-1);
        }
        return commandLine;
    }

    /**
     * Get option int.
     *
     * @param endpoint the endpoint
     * @return the int
     */
    private static int getOption(String endpoint){
        int option=0;
        switch (endpoint.trim().toUpperCase()){
            case "AUTH": option = 1; break;
            case "CAPTURE": option = 2; break;
            case "VOID": option = 3; break;
            case "REFUND": option = 4; break;
            case "INQUIRE": option = 5; break;
            case "SETTLESTAT": option = 6; break;
            case "FUNDING": option = 7; break;
            case "PROFILE-GET": option = 8; break;
            case "PROFILE-PUT": option = 9; break;
            case "PROFILE-DELETE": option = 10; break;
            case "SIGCAP": option = 11; break;
            case "OPENBATCH": option = 12; break;
            case "CLOSEBATCH": option = 13; break;
            case "BIN": option = 14; break;
            default:
                err.println("Invalid endpoint: " + endpoint);
        }
        return option;
    }
}
