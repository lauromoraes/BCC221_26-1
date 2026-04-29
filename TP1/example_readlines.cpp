#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>

int main() {
    std::ifstream file("data.csv");
    std::string line;

    // 1. Read file line by line
    while (std::getline(file, line)) {
        std::stringstream ss(line);
        std::string field;
        std::vector<std::string> row;

        // 2. Split line by comma
        while (std::getline(ss, field, ',')) {
            row.push_back(field);
        }

        // Process your row data here
        for (const auto& f : row) std::cout << "[" << f << "] ";
        std::cout << std::endl;
    }
    return 0;
}
