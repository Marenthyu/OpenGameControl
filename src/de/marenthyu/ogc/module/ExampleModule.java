package de.marenthyu.ogc.module;

public class ExampleModule implements GameModule{
    private static String name = "Example Module";
    public ExampleModule() {
        System.out.println("Example Module!");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Example Module";
    }

    @Override
    public void initialize() {
        System.out.println("Init");
    }
}
