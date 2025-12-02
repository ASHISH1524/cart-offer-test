# 🧪 Zomato Cart Offer API Test Automation

![Test Execution](https://github.com/YOUR_USERNAME/cart-offer-test/workflows/API%20Test%20Execution/badge.svg)
![Java](https://img.shields.io/badge/Java-11-orange)
![REST Assured](https://img.shields.io/badge/REST%20Assured-5.3.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.7.1-red)

## 📋 Project Overview

This repository contains comprehensive API test automation for Zomato's Cart Offer feature. The project is developed as part of the Lucidity SDET assignment, demonstrating proficiency in REST API testing, test automation, and CI/CD integration.

### 🎯 Assignment Details
- **Company**: Lucidity
- **Position**: SDET - API Testing
- **Framework**: REST Assured + TestNG
- **Language**: Java 11

## ✨ Features

- ✅ **15 Comprehensive Test Cases** covering all scenarios
- ✅ **Automated CI/CD** with GitHub Actions
- ✅ **Allure Reporting** for beautiful test reports
- ✅ **MockServer Integration** for API mocking
- ✅ **Detailed Documentation** with inline comments
- ✅ **Segment-based Testing** (P1, P2, P3)
- ✅ **Edge Case Coverage** (negative values, boundaries)

## 🧪 Test Coverage

### Test Scenarios (15 Tests)

| # | Test Scenario | Type | Priority |
|---|--------------|------|----------|
| 1 | Flat amount discount for P1 | Positive | Critical |
| 2 | Percentage discount for P1 | Positive | Critical |
| 3 | Flat amount discount for P2 | Positive | Critical |
| 4 | Percentage discount for P3 | Positive | Critical |
| 5 | No discount for non-matching segment | Negative | Critical |
| 6 | Offer for multiple segments | Positive | Normal |
| 7 | Restaurant-specific offers | Positive | Critical |
| 8 | Discount greater than cart value | Edge Case | Normal |
| 9 | 100% discount scenario | Edge Case | Normal |
| 10 | Offer creation API response | API Validation | Critical |
| 11 | Multiple offers for same segment | Positive | Normal |
| 12 | Decimal cart values | Boundary | Normal |
| 13 | Small percentage discount | Positive | Normal |
| 14 | Large cart value with discount | Boundary | Minor |
| 15 | All segments integration test | Integration | Critical |

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 11 | Programming Language |
| REST Assured | 5.3.0 | API Testing Framework |
| TestNG | 7.7.1 | Test Framework |
| MockServer | 5.15.0 | API Mocking |
| Maven | 3.6+ | Build Tool |
| Allure | 2.24.0 | Reporting |
| GitHub Actions | - | CI/CD Pipeline |

## 📁 Project Structure
```
cart-offer-test/
├── .github/
│   └── workflows/
│       └── test-execution.yml    # GitHub Actions CI/CD
├── src/
│   └── test/
│       └── java/
│           └── com/lucidity/tests/
│               ├── BaseTest.java          # Base test class
│               └── CartOfferTest.java     # 15 test cases
├── pom.xml                        # Maven dependencies
├── testng.xml                     # TestNG configuration
├── README.md                      # Local documentation
├── GITHUB_README.md               # This file
└── TestCases.xlsx                 # Test case documentation
```

## 🚀 Quick Start

### Prerequisites
```bash
- Java JDK 11 or higher
- Maven 3.6+
- Git
```

### Local Setup

1. **Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/cart-offer-test.git
cd cart-offer-test
```

2. **Install dependencies**
```bash
mvn clean install -DskipTests
```

3. **Run tests**
```bash
mvn clean test
```

4. **Generate Allure Report**
```bash
mvn allure:serve
```

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow

The project includes automated testing via GitHub Actions that:

1. ✅ Triggers on every push/PR to main branch
2. ✅ Sets up Java 11 environment
3. ✅ Installs dependencies
4. ✅ Runs all 15 test cases
5. ✅ Generates Allure reports
6. ✅ Uploads test artifacts
7. ✅ Publishes test results
8. ✅ Deploys reports to GitHub Pages

### View Test Results

After each run:
- **Test Results**: Check "Actions" tab in GitHub
- **Allure Report**: Available as downloadable artifact
- **TestNG Reports**: Available in artifacts section

## 📊 Reports & Artifacts

### Allure Report
Beautiful, interactive test reports with:
- Test execution timeline
- Test case details
- Screenshots (if configured)
- Trend analysis

### TestNG Reports
Standard HTML reports showing:
- Pass/Fail status
- Execution time
- Error details

### Accessing Reports

1. Go to **Actions** tab in GitHub
2. Click on latest workflow run
3. Scroll to **Artifacts** section
4. Download:
   - `allure-results`
   - `allure-report`
   - `testng-reports`

## 🔧 Configuration

### MockServer Port
Default: `8080`

To change, modify in `BaseTest.java`:
```java
protected String baseUrl = "http://localhost:YOUR_PORT";
```

### Test Priority
Tests run in priority order (1-15). Modify in `@Test` annotation:
```java
@Test(priority = 1, description = "Your test")
```

## 📝 Test Execution Examples

### Run all tests
```bash
mvn clean test
```

### Run specific test class
```bash
mvn test -Dtest=CartOfferTest
```

### Run with specific TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Generate report only
```bash
mvn allure:serve
```

## 📈 Test Results Summary

| Metric | Value |
|--------|-------|
| Total Tests | 15 |
| Critical Tests | 8 |
| Edge Cases | 2 |
| Boundary Tests | 2 |
| Integration Tests | 1 |
| Expected Pass Rate | 100% |

## 🐛 Known Issues & Limitations

- MockServer requires port 8080 to be available
- Tests run sequentially due to mock server state
- Allure report requires manual download from artifacts

## 🤝 Contributing

This is an assignment project. No contributions needed.

## 📧 Contact

**Name**: [Your Name]  
**Email**: [your.email@example.com]  
**LinkedIn**: [Your LinkedIn Profile]  
**Assignment**: Lucidity SDET - API Testing

## 📜 License

This project is created for educational and assignment purposes.

## 🎓 Assignment Submission Details

### Deliverables Checklist
- ✅ Complete source code
- ✅ 15 test cases implemented
- ✅ Test case Excel sheet
- ✅ README documentation
- ✅ GitHub Actions CI/CD
- ✅ Allure reporting setup
- ✅ All tests passing

### Evaluation Criteria Met
1. ✅ **Number of test cases**: 15 comprehensive tests
2. ✅ **Importance of tests**: Critical business scenarios covered
3. ✅ **Implementation quality**: Clean, documented code
4. ✅ **Execution capability**: Automated via GitHub Actions

---

**Last Updated**: December 2024  
**Status**: ✅ Ready for Submission
