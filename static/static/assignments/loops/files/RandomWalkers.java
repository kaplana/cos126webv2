public class RandomWalkers {
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);        // number of steps
        int trials = Integer.parseInt(args[0]);   // number of trials

        int totalSquaredDistance = 0;

        // current location
        int x = 0, y = 0;
        for (int t = 0; t < trials; t++) {

            // take n random steps
            for (int i = 0; i < n; i++) {
                double r = Math.random();
                if (0 <= r < 0.25)
                    x++;
                else if (0.25 < r < 0.50)
                        x--;
                else if (0.50 < r < 0.75)
                        y++;
                else
                    y--;
		}

            totalSquaredDistance = (x*x + y*y);
        }

        double meanSquaredDistance = totalSquaredDistance / trials;
        System.out.println("mean squared distance = " + meanSquaredDistance);
    }
}
