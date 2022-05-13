package Main;

public class SimulatedAnnealing {
    public static final double RATE_OF_COOLING = 0.0001;
    public static final double INITIAL_TEMPERATURE = 1500.0;
    public static final double MIN_TEMPERATURE = 1.0;
    public static final int MAX_ADJACENT_ROUTES = 1000;

    // Function to find the best route
    public Route findRoute(double temperature, Route currentRoute) {
        Route shortestRoute = new Route(currentRoute);
        Route adjacentRoute;

        int count = 0;
        while (temperature > MIN_TEMPERATURE) {
            boolean accepted = false;

            // Duration of one stage
            for (int i = 0; i < MAX_ADJACENT_ROUTES; i++) {
                adjacentRoute = createAdjacentRoute(new Route(currentRoute));

                // Decide if new adjacent route will be accepted
                if (acceptRoute(currentRoute.getTotalDistance(), adjacentRoute.getTotalDistance(), temperature)) {
                    currentRoute = new Route(adjacentRoute);
                    accepted = true;
                }
                // Check if is current route better than the shortest route
                if (currentRoute.getTotalDistance() < shortestRoute.getTotalDistance()) {
                    shortestRoute = new Route(currentRoute);
                }
                // If accepted, stop generating adjacent routes
                if (accepted) {
                    break;
                }
            }
            // If not accepted, return shortestRoute
            if (!accepted) {
                return shortestRoute;
            }
            // Reduce the temperature
            temperature *= (1 - RATE_OF_COOLING);
            count++;
        }
        return shortestRoute;
    }

    // Decide if adjacent route will be accepted
    public boolean acceptRoute(double currentDistance, double adjacentDistance, double temperature) {
        double acceptableProbability = 1.0;
        boolean acceptedRoute = false;

        // Adjacent distance is worse than current distance
        if (adjacentDistance >= currentDistance) {
            acceptableProbability = Math.exp(-(adjacentDistance - currentDistance) / temperature);
        }

        // If adjacent distance is better auto accept (1.0 >= randomNum)
        // Else compare acceptableProbability
        double randomNum = Math.random();
        if (acceptableProbability >= randomNum)  {
            acceptedRoute = true;
        }
        return acceptedRoute;
    }

    // Generate new adjacent route
    public Route createAdjacentRoute(Route route) {
        int firstIndex = 0;
        int secondIndex = 0;

        // Generate random & different indexes
        while (firstIndex == secondIndex) {
            firstIndex = (int) (route.getCities().size() * Math.random());
            secondIndex = (int) (route.getCities().size() * Math.random());
        }
        // Switch cities positions
        City firstCity = route.getCities().get(firstIndex);
        City secondCity = route.getCities().get(secondIndex);
        route.getCities().set(secondIndex, firstCity);
        route.getCities().set(firstIndex, secondCity);

        return route;
    }

    // Function to find the best route with own parameters
    public Route findRouteTester(double maxTemp, double minTemp, double rateOfCooling, int maxAdjRoutes, Route currentRoute) {
        Route shortestRoute = new Route(currentRoute);
        Route adjacentRoute;

        int count = 0;
        while (maxTemp > minTemp) {
            boolean accepted = false;
            for (int i = 0; i < maxAdjRoutes; i++) {
                adjacentRoute = createAdjacentRoute(new Route(currentRoute));
                if (acceptRoute(currentRoute.getTotalDistance(), adjacentRoute.getTotalDistance(), maxTemp)) {
                    currentRoute = new Route(adjacentRoute);
                    accepted = true;
                }
                if (currentRoute.getTotalDistance() < shortestRoute.getTotalDistance()) {
                    shortestRoute = new Route(currentRoute);
                }
                if (accepted) {
                    break;
                }
            }
            if (!accepted) {
                return shortestRoute;
            }
            else {
                maxTemp *= (1 - rateOfCooling);
                count++;
            }
        }
        return shortestRoute;
    }
}
