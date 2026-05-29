public class DistanciaGeografica {
    
    public static double distancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raio da Terra em km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance/2) * Math.sin(latDistance/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance/2) * Math.sin(lonDistance/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
    
    public static void main(String[] args) {
        // Exemplo 1: Distância entre São Paulo e Rio de Janeiro
        double saoPauloLat = -23.5505;
        double saoPauloLon = -46.6333;
        double rioJaneiroLat = -22.9068;
        double rioJaneiroLon = -43.1729;
        
        double distanciaSP_RJ = distancia(saoPauloLat, saoPauloLon, rioJaneiroLat, rioJaneiroLon);
        System.out.printf("Distância entre São Paulo e Rio de Janeiro: %.2f km%n", distanciaSP_RJ);
        
        // Exemplo 2: Distância entre Nova York e Londres
        double novaYorkLat = 40.7128;
        double novaYorkLon = -74.0060;
        double londresLat = 51.5074;
        double londresLon = -0.1278;
        
        double distanciaNY_Londres = distancia(novaYorkLat, novaYorkLon, londresLat, londresLon);
        System.out.printf("Distância entre Nova York e Londres: %.2f km%n", distanciaNY_Londres);
        
        // Exemplo 3: Distância entre duas cidades brasileiras (Brasília e Belo Horizonte)
        double brasiliaLat = -15.8267;
        double brasiliaLon = -47.9218;
        double beloHorizonteLat = -19.9167;
        double beloHorizonteLon = -43.9345;
        
        double distanciaBSB_BH = distancia(brasiliaLat, brasiliaLon, beloHorizonteLat, beloHorizonteLon);
        System.out.printf("Distância entre Brasília e Belo Horizonte: %.2f km%n", distanciaBSB_BH);
        
        // Exemplo 4: Mesma localidade (deve resultar em 0 km)
        double distanciaZero = distancia(saoPauloLat, saoPauloLon, saoPauloLat, saoPauloLon);
        System.out.printf("Distância entre um ponto e ele mesmo: %.2f km%n", distanciaZero);
    }
}