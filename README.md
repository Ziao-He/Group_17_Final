# Campus Marketplace System

> A secure second-hand trading platform for university students

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/UI-Java%20Swing-blue.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![License](https://img.shields.io/badge/license-Academic-green.svg)](LICENSE)

**INFO 5100 Final Project - Fall 2025 | Group 17**

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [System Architecture](#-system-architecture)
- [Installation](#-installation)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [Team Contributions](#-team-contributions)
- [Documentation](#-documentation)

---

## 🎯 Overview

### Problem Statement

University students frequently trade second-hand items through fragmented channels (social media, messaging apps), which creates challenges:

- ❌ Difficult to search and compare prices
- ❌ Lack of trust between parties
- ❌ No organized transaction workflow
- ❌ Risk of scams and disputes

### Our Solution

The **Campus Marketplace System** provides a dedicated, campus-specific platform with:

- ✅ **End-to-end workflow**: Listing → Searching → Ordering → Completion
- ✅ **Role-based security**: Buyers, Sellers, Admins, Support Staff
- ✅ **Built-in communication**: In-system messaging between users
- ✅ **Budget management**: Spending limits and tracking
- ✅ **Content moderation**: Admin review and policy enforcement
- ✅ **Dispute resolution**: Help Center for complaints

---

## ✨ Features

### 🛍️ For Buyers (Enterprise 1)

**Core Features:**
- Browse all approved listings with detailed information
- Advanced search with filters (category, price range, condition)
- Shopping cart with real-time budget checking
- One-click checkout for multiple items
- Favorites/wishlist management

**Order Management:**
- Real-time order tracking (Pending → Accepted → Completed)
- Complete orders to earn reward points (5% cashback)
- Cancel orders to restore budget
- View complete purchase history
- Report problematic sellers

**Budget Control:**
- Set maximum spending budget
- Automatic budget enforcement at checkout
- Budget restoration on order cancellation
- Real-time budget tracking

**Statistics Dashboard:**
- Total purchases
- Completed orders
- Reward points earned
- Total spending amount

---

### 🏪 For Sellers (Enterprise 2)

**Listing Management:**
- Create listings with title, description, price, photos
- Edit existing listings (price, description, quantity)
- Delete listings
- Bulk operations for multiple listings

**Order Processing:**
- View incoming order requests in queue
- Accept or reject buyer requests
- Track accepted orders
- Mark orders as sold/completed
- View order history and analytics

**Sales Analytics:**
- Total revenue
- Number of sales
- Average order value
- Performance metrics

---

### 👨‍💼 For Platform Admins (Enterprise 3)

**User Management:**
- Review newly posted product listings
- Approve compliant listings
- Reject policy-violating content
- Monitor and investigate reported listings
- Enforce platform content policies
- Track moderation outcomes with operator identity

**Content Moderation:**
- Every approved or rejected WorkRequest is fully traceable
- Each WorkRequest records:
    Request creator
    Processing admin
    Approval / rejection decision
    Timestamp of each operation
    Final resolution result
- Enables end-to-end approval audit tracking
- Supports accountability, transparency, and compliance verification

**System Analytics:**
- Total number of users by role (Buyer / Seller / Admin / Support)
- Active listings count
- Policy violation statistics and trends
- WorkRequest processing efficiency metrics (optional advanced KPI)

**Super Administrator:**
- View all WorkRequests across the entire platform
- Audit:
    Who created each WorkRequest
    Who processed and approved it
    When and how each decision was made
- Monitor:
    Admin behavior and approval performance
    Policy enforcement consistency
- Detect abnormal approval patterns or potential abuse
- Perform platform-wide governance and supervision

---

### 🆘 For Help Center (Enterprise 4)

**Communication Services:**
- Monitor buyer-seller messages
- Facilitate communication
- Review flagged messages

**Issue Resolution:**
- Receive and triage complaints
- Investigate policy violations
- Mediate disputes

---

## 🏗️ System Architecture

### Three-Layer Architecture

```
┌─────────────────────────────────────┐
│     Presentation Layer (UI)         │
│  Java Swing with CardLayout         │
│  - BuyerJPanel, SellerJPanel        │
│  - Admin Dashboards                 │
└─────────────────────────────────────┘
              ↕
┌─────────────────────────────────────┐
│      Business Layer (Services)      │
│  - ProductDiscoveryService          │
│  - OrderFulfillmentService          │
│  - ContentModerationService         │
│  - WorkeRequestService              │
└─────────────────────────────────────┘
              ↕
┌─────────────────────────────────────┐
│    Persistence Layer (DAO)          │
│  CSV-based data storage             │
│  - UserAccountFileDAO               │
│  - ListingFileDAO                   │
│  - OrderFileDAO                     │
│  - WorkeRequestService              │
└─────────────────────────────────────┘
```

### Enterprise Communication

**WorkRequest System** enables cross-organization communication:

```
Enterprise 1 (Buyer)
    ↓ OrderReviewRequest
Enterprise 2 (Seller)
    ↓ OrderProcessingResultRequest
Enterprise 1 (Buyer)
    ↓ OrderReportRequest
Enterprise 4 (Help Center)
```

### Data Flow

```
User Action
    ↓
UI Panel (Swing JPanel)
    ↓
Service Layer (Business Logic)
    ↓
Directory (In-Memory Storage)
    ↓
DAO (CSV Persistence)
    ↓
CSV File (data/*.csv)
```

---

## 📦 Installation

### System Requirements

- **Java**: JDK 17 or higher
- **IDE**: Apache NetBeans 19+ (recommended)
- **OS**: Windows 10/11, macOS, Linux
- **RAM**: 2GB minimum
- **Storage**: 100MB

### Quick Start

```bash
# 1. Clone repository
git clone https://github.com/your-team/campus-marketplace.git
cd campus-marketplace

# 2. Build project
ant clean jar

# 3. Run application
java -jar dist/MarketplaceSystem.jar
```

### First Launch

The system will:
1. Create `data/` directory
2. Generate sample CSV files
3. Initialize test users and listings
4. Launch login screen

---

## 🚀 Usage

### Login Credentials (Test Accounts)

| Role | Username | Password | Description |
|------|----------|----------|-------------|
| System Admin | `s` | `123` | Full system access |
| Buyer | `buyer1` | `password123` | Test buyer account |
| Seller | `seller1` | `123` | Test seller account |
| Platform Admin | `platformadmin` | `123` | User management |
| Content Moderator | `c` | `1234` | Content review |

### User Guide

#### 👤 As a Buyer

**Step 1: Set Your Budget**
1. Click "Personal" tab
2. Click "Edit" button
3. Enter your maximum budget (e.g., $1000)
4. Click "Save"

**Step 2: Browse Products**
1. View all listings on Browse tab
2. Filter by category, price, status
3. Click "Add to Cart" for items you want
4. Click btn to add to favorites

**Step 3: Checkout**
1. Click "Cart" tab
2. Review items and total price
3. Budget check happens automatically
4. Click "Finish" to place orders

**Step 4: Track Orders**
1. Click "Track" tab
2. View order status (Pending/Accepted/Rejected)
3. Click "Complete" when you receive the item
4. Click "Cancel" to cancel pending orders
5. Click "Report" to report issues

#### 🏪 As a Seller

**Step 1: Create Listing**
1. Click "Create Listing" tab
2. Fill in product details
3. Upload product photo (optional)
4. Click "Submit"
5. Wait for admin approval

**Step 2: Manage Listings**
1. View all your listings
2. Edit price or description
3. Delete unwanted listings

**Step 3: Process Orders**
1. Check "Order Queue" for incoming requests
2. Click "Accept" to approve buyer request
3. Click "Reject" to decline
4. Coordinate with buyer for delivery

#### 🏪 As a As a Platform Super Admin
**Step 1: Login as Super Admin**
1. Enter your username and password
2. Click "Login"
3. System automatically redirects to Platform Management Dashboard
4. You now have access to all organizations and all requests

**Step 2: Manage User Accounts**
1. Click "User Management" tab
2. View all registered users across the platform
3. Select a user and click:
    Approve to activate new accounts
    Reject to deny registration
    Ban to disable a violating account
    Reactivate to restore suspended accounts
4. All actions are automatically recorded in WorkRequest history

**Step 3: Moderate Platform Content**
1. Click "Content Moderation" tab
2. Review all submitted listings
3. Select a listing and:
    Approve compliant content
    Reject policy-violating content
4. Rejected listings are automatically removed from public view

**Step 4: Enforce Platform Policies**
1. Click "Policy Enforcement" tab
2. View all reported users and listings
3. Review violation details
4. Apply actions:
    Warning
    Account Ban
    Listing Takedown
5. System automatically updates:
    User status
    Listing status
    Violation history
**Step 5: Track All Work Requests (Global View)**
1. Click "Analytics" tab
2. View real-time statistics:
    Total users by role
    Active listings count
    Total transactions
    Policy violations count

---

## 📁 Project Structure

### Key Packages

```
basement_class/                      # Core system framework
├── EcoSystem.java                  # Singleton root
├── Network.java                    # Network layer
├── Enterprise.java                 # Enterprise base
├── Organization.java               # Organization base
├── Role.java                       # Role base
├── UserAccount.java                # User account base
├── Profile.java                    # User profile base
├── WorkRequest.java                # Communication base
├── BaseEntity.java                 # Entity base with timestamps
├── UserAccountDirectory.java
├── WorkRequestDirectory.java
├── OrderDirectory.java
│
├── Enterprise_1/                   # Buyer Operations
│   ├── Account/
│   │   ├── BuyerAccount.java
│   │   └── BuyerProfile.java
│   ├── Role/
│   │   ├── BuyerRole.java
│   │   └── OrderTrackerRole.java
│   ├── Organization/
│   │   ├── ShoppingServicesOrganization.java
│   │   └── OrderSelfTrackerOrganization.java
│   ├── Enterprise/
│   │   └── BuyerOperationsEnterprise.java
│   └── WorkRequest/
│       └── OrderProcessingResultRequest.java
│
├── Enterprise_2/                   # Seller Operations
│   ├── Listing.java
│   ├── ListingDirectory.java
│   ├── Account/
│   │   ├── SellerAccount.java
│   │   └── OrderProcessorAccount.java
│   ├── Role/
│   │   ├── SellerRole.java
│   │   ├── ListingManagerRole.java
│   │   └── OrderProcessorRole.java
│   ├── Organization/
│   │   ├── SellerOrganization.java
│   │   └── OrderManagementOrganization.java
│   ├── Enterprise/
│   │   └── MarketplacePlatformEnterprise.java
│   └── WorkRequest/
│       └── OrderReviewRequest.java
│
├── Enterprise_3/                   # Platform Management
│   ├── Account/
│   │   ├── PlatformAdminAccount.java
│   │   └── SystemAdminAccount.java
│   ├── Role/
│   │   ├── PlatformAdminRole.java
│   │   ├── SystemAdminRole.java
│   │   ├── AccountAdminRole.java
│   │   ├── ContentModeratorRole.java
│   │   └── PolicyEnforcerRole.java
│   ├── Organization/
│   │   ├── UserControlOrganization.java
│   │   └── ContentControlOrganization.java
│   ├── Enterprise/
│   │   └── PlatformManagementEnterprise.java
│   └── WorkRequest/
│       └── PolicyViolationRequest.java
│
├── Enterprise_4/                   # Help Center
│   ├── Account/
│   │   └── HelpCenterAccount.java
│   ├── Role/
│   │   ├── MessageHandlerRole.java
│   │   └── ComplaintHandlerRole.java
│   ├── Organization/
│   │   ├── CommunicationServicesOrganization.java
│   │   └── IssueResolutionOrganization.java
│   ├── Enterprise/
│   │   └── HelpCenterEnterprise.java
│   ├── WorkRequest/
│   │   └── OrderReportRequest.java
│   └── MessageDirectory.java
│
└── DAO/                            # Data Access Layer
    ├── UserAccountDAO.java (interface)
    ├── UserAccountFileDAO.java
    ├── UserAccountService.java
    ├── ListingDAO.java
    ├── ListingFileDAO.java
    ├── ListingHelperFunction.java
    ├── OrderDAO.java
    ├── OrderFileDAO.java
    ├── OdedrService.java
    ├── WorkRequestDAO.java
    └── WorkRequestFileDAO.java

service.E1/                         # Enterprise 1 Services
└── OrderService.java

service.E2/                         # Enterprise 2 Services
├── ListingManagementService.java
└── OrderFulfillmentService.java

service.E3/                         # Enterprise 3 Services
├── ContentModerationService.java
├── PolicyEnforcementService.java
├── RegistrationService.java
├── UserManagementService.java
└── WorkRequestRouter.java

common_class/                       # Shared entities
└── Order.java                      # Order entity

UI/                                 # User Interface Layer
├── Enterprise1/                    # Buyer UI (10 panels)
│   ├── BuyerJPanel.java
│   ├── BrowseWorkArea.java
│   ├── ShoppingCartWorkArea.java
│   ├── ListingDetailWorkArea.java
│   ├── TrackJPanel.java
│   ├── TrackingOrderStatus.java
│   ├── TrackManuJPanel.java
│   ├── PersonalJPanel.java
│   ├── ProductSearchJPanel.java
│   └── ReportViolationWorkArea.java
│
├── Enterprise2/                    # Seller UI (9 panels)
│   ├── SellerJPanel.java
│   ├── ManageListingJPanel.java
│   ├── ManageAllListingJPanel.java
│   ├── CreateNewListingJPanel.java
│   ├── CreateBulkListingJPanel.java
│   ├── OrderHistoryJPanel.java
│   ├── OrderProcessorJPanel.java
│   ├── ReviewOrdersJPanel.java
│   └── ListingManagerJPanel.java
│
├── Enterprise3/                    # Admin UI (8 panels)
│   ├── AdminJPanel.java
│   ├── AccountAdminWorkAreaPanel.java
│   ├── ContentModerationJPanel.java
│   ├── PolicyEnforcementJPanel.java
│   ├── PlatformManagmentJPanel.java
│   ├── RegistrationReviewJPanel.java
│   └── HistoryPanel.java
│
├── Enterprise4/                    # Help Center UI (7 panels)
│   ├── HelpCenterWorkAreaPanel.java
│   ├── BuyerSellerChatPanel.java
│   ├── BuyerSellerMonitorPanel.java
│   ├── CommunicationServicesOrgPanel.java
│   ├── IssueResolutionOrgPanel.java
│   ├── MessageFlagHandlingPanel.java
│   └── OrderReportHandlingPanel.java
│
└── UI.main/                        # Main application
    ├── LoginPage.java
    ├── main.java
    ├── SystemInitializer.java
    ├── Enterprise1Initializer.java
    ├── Enterprise2Initializer.java
    ├── Enterprise3Initializer.java
    ├── Enterprise4Initializer.java
    └── FakeDataGenerator.java
```

---

## 🔧 Configuration

### Data Files Location

All CSV files are stored in the `data/` directory:

```
data/
├── user_accounts.csv       # User accounts and profiles
├── listings.csv            # Product listings
├── orders.csv              # Order records
├── messages.csv            # User messages
├── complaints.csv          # User complaints
└── work_requests.csv       # WorkRequest queue
```

### CSV File Format

**user_accounts.csv:**
```csv
id,username,email,password,phone,orgId,status,role,warningCount,
buyerPreferredCategories,buyerMaxBudget,buyerLocation,buyerPayMethod,
profileFullName,profileEmail,profilePhone
```

**listings.csv:**
```csv
id,sellerId,title,description,imagePath,price,status,
submitTime,category,condition,quantity
```

**orders.csv:**
```csv
orderId,listingId,buyerId,sellerId,totalPrice,quantity,status,
createdAt,deliveryMethod,meetingLocation
```

**orders.csv:**
```csv
id,type,senderId,receiverId,status,requestDate,resolveDate,reviewerId,reviewAction,reviewComment,extra1,extra2,extra3,
extra4
```
---

## 🧪 Testing

### Test Data Generation

The system uses **Faker** library to generate realistic test data:

```java
// Automatically generates:
- 10+ test users across all roles
- 20+ sample product listings
- Various order statuses for testing
```

### Manual Testing Checklist

**Buyer Operations:**
- [ ] Login as buyer
- [ ] Browse listings
- [ ] Add to cart (budget check)
- [ ] Checkout multiple items
- [ ] Track order status
- [ ] Complete order (earn points)
- [ ] Cancel order (restore budget)

**Seller Operations:**
- [ ] Login as seller
- [ ] Create new listing
- [ ] Edit existing listing
- [ ] Process incoming orders
- [ ] Accept/reject requests

**Admin Operations:**
- [ ] Review pending listings
- [ ] Approve/reject content
- [ ] Manage user accounts
- [ ] Handle policy violations

---

## 📚 Documentation

### API Documentation

**Key Classes:**

- `EcoSystem` - System singleton, central hub
- `UserAccount` - Abstract user base class
- `BuyerAccount` - Buyer-specific implementation
- `Order` - Transaction entity
- `Listing` - Product listing entity
- `WorkRequest` - Cross-enterprise communication

### Design Patterns Used

- **Singleton**: EcoSystem
- **Factory**: UserAccount creation
- **Template Method**: Role.createWorkArea()
- **Observer**: WorkRequest notifications
- **MVC**: UI separation
- **DAO**: Data persistence abstraction

---

## 👥 Team Contributions

**Group 17 Members:**

### Enterprise 1 - Buyer Operations
**Developer**: Ziao He

**Responsibilities:**
- BuyerAccount, BuyerProfile implementation with budget management
- Complete shopping workflow (Browse → Cart → Checkout → Track)
- Order tracking system with dual panels (status + history)
- Personal information management with input validation
- OrderProcessingResultRequest and OrderReportRequest
- Reward points system (5% cashback)

**Files Delivered (10 UI panels + 6 core classes):**
- UI Panels: `BuyerJPanel.java`, `BrowseWorkArea.java`, `ShoppingCartWorkArea.java`, `ListingDetailWorkArea.java`, `TrackJPanel.java`, `TrackingOrderStatus.java`, `TrackManuJPanel.java`, `PersonalJPanel.java`, `ProductSearchJPanel.java`, `ReportViolationWorkArea.java`
- Account: `BuyerAccount.java`, `BuyerProfile.java`
- Roles: `BuyerRole.java`, `ProductSearcherRole.java`, `OrderTrackerRole.java`
- Organizations: `ShoppingServicesOrganization.java`, `OrderSelfTrackerOrganization.java`
- Enterprise: `BuyerOperationsEnterprise.java`
- WorkRequest: `OrderProcessingResultRequest.java`
- Service: `OrderService.java`

**Key Features Implemented:**
- Budget validation and enforcement (budget=0 requires setup)
- Shopping cart with real-time total calculation
- Dual order tracking (notifications + history)
- Smart order cancellation (only restores listing if Reserved)
- Complete input validation (Name, Email, Phone, Budget)
- Intelligent filter (Canceled includes Rejected orders)

---

### Enterprise 2 - Seller Operations
**Developer**: Liyun Li

**Responsibilities:**
- seller submit Listing (Pending) and upload images
- Seller manages its own listings
- Listing Manager Submit bulk listing operations
- Listing Manager manage all listings
- Listing Manager make complaints about listings
- Order processor manages orders from buyers through OrderReviewRequest(Choose whether to approve the transaction or not)
- Order processor statistics on order status and completion rate
**Files Delivered (8 UI panels + 6 core classes):**
- UI Panels: `CreateBulkListingJPanel`,`CreateNewListingJPanel`,`ListingManagerJPanel`,`ManageAllListingJPanel`,`ManageListingJPanel`,`OrderHistoryJPanel`,`OrderProcessorJPanel`,``ReviewOrdersJPanel,SellerJPanel`
- Core: `Listing.java`, `ListingDirectory.java`
- Account: `SellerAccount.java`, `OrderProcessorAccount.java`
- Roles: `SellerRole.java`, `ListingManagerRole.java`, `OrderProcessorRole.java`
- Organizations: `SellerOrganization.java`, `OrderManagementOrganization.java`
- Enterprise: `MarketplacePlatformEnterprise.java`
- WorkRequest: `OrderReviewRequest.java`
- Services: `ListingManagementService.java`, `OrderFulfillmentService.java`

**Key Features Implemented:**
- Photo upload for listings
- Order queue management for sellers
- Make complaints about listings
- Communicate with the buyer
- Listing status workflow (Pending → Approved → Reserved → Sold)
- Sales statistics dashboard

---

### Enterprise 3 - Platform Management
**Developer**: Yiyang Lin

**Responsibilities:**
- User registration and authentication
- Account management (CRUD operations)
- Content moderation workflow
- Policy enforcement and warnings
- System analytics and reporting
- WorkeRequest History tracing

**Files Delivered (8 UI panels + 6 core classes):**
- UI Panels: `AdminJPanel.java`, `AccountAdminWorkAreaPanel.java`, `ContentModerationJPanel.java`, `PolicyEnforcementJPanel.java`, `PlatformManagmentJPanel.java`, `RegistrationReviewJPanel.java`, `HistoryPanel.java`
- Account: `PlatformAdminAccount.java`, `SystemAdminAccount.java`
- Roles: `PlatformAdminRole.java`, `SystemAdminRole.java`, `AccountAdminRole.java`, `ContentModeratorRole.java`, `PolicyEnforcerRole.java`
- Organizations: `UserControlOrganization.java`, `ContentControlOrganization.java`
- Enterprise: `PlatformManagementEnterprise.java`
- Services: `ContentModerationService.java`, `PolicyEnforcementService.java`, `RegistrationService.java`, `UserManagementService.java`
- WorkeRequest:`ContentModerationWorkRequest.java`,`PolicyEnforcementServiceWorkRequest.java`,`RegistrationWorkRequest.java`,`UserManagementWorkRequest.java`

**Key Features Implemented:**
- User registration approval workflow
- Account suspension and warning system
- Listing approval/rejection
- Policy violation handling

---

### Enterprise 4 - Help Center & Core Architecture
**Developer**: Yujie Liang

**Responsibilities:**
- Help Center services (messaging, complaints)
- Core system architecture (EcoSystem, Network, Enterprise, Organization)
- WorkRequest communication system

**Files Delivered (7 UI panels + 20+ core classes):**
- UI Panels: `HelpCenterWorkAreaPanel.java`, `BuyerSellerChatPanel.java`, `BuyerSellerMonitorPanel.java`, `CommunicationServicesOrgPanel.java`, `IssueResolutionOrgPanel.java`, `MessageFlagHandlingPanel.java`, `OrderReportHandlingPanel.java`
- Core Architecture: `EcoSystem.java`, `Network.java`, `Enterprise.java`, `Organization.java`, `BaseEntity.java`
- Abstract Classes: `UserAccount.java`, `Role.java`, `Profile.java`, `WorkRequest.java`
- Directories: `UserAccountDirectory.java`, `WorkRequestDirectory.java`, `OrderDirectory.java`
- Account: `HelpCenterAccount.java`
- Roles: `MessageHandlerRole.java`, `ComplaintHandlerRole.java`
- Organizations: `CommunicationServicesOrganization.java`, `IssueResolutionOrganization.java`
- Enterprise: `HelpCenterEnterprise.java`
- WorkRequest: `OrderReportRequest.java`
- DAO: `UserAccountDAO.java`, `UserAccountFileDAO.java`, `UserAccountService.java`, `ListingDAO.java`, `ListingFileDAO.java`, `OrderDAO.java`, `OrderFileDAO.java`, `WorkRequestDAO.java`, `WorkRequestFileDAO.java`
- Services: `WorkRequestRouter.java`
- Initialization: `SystemInitializer.java`, `Enterprise1Initializer.java`, `Enterprise2Initializer.java`, `Enterprise3Initializer.java`, `Enterprise4Initializer.java`
- Main: `LoginPage.java`, `main.java`

**Key Features Implemented:**
- Singleton pattern for EcoSystem
- Factory pattern for user/role creation
- Complete DAO abstraction layer
- CSV-based persistence for all entities
- Automated test data generation (Faker)
- Cross-enterprise WorkRequest routing
- Message monitoring system
- Complaint submission and tracking

---

## 📊 Project Statistics

### Code Metrics

| Metric | Count |
|--------|-------|
| Total Classes | 100+ |
| Lines of Code | 15,000+ |
| UI Panels | 30+ |
| Enterprises | 4 |
| Organizations | 8 |
| Unique Roles | 12 |
| WorkRequest Types | 6+ |

### Feature Compliance

| Requirement | Status |
|-------------|--------|
| 4 Enterprises | ✅ Implemented |
| 8 Organizations | ✅ Implemented |
| 12 Unique Roles | ✅ Implemented |
| 6+ WorkRequests | ✅ Implemented |
| Cross-Enterprise Communication | ✅ Implemented |
| Role-based Authentication | ✅ Implemented |
| CRUD Operations | ✅ Implemented |
| Input Validation | ✅ Implemented |
| Reporting Module | ✅ Implemented |
| Test Data (Faker) | ✅ Implemented |

---

## 🔑 Key Technical Features

### Budget Management System

```java
// Automatic budget checking
if (totalPrice > buyer.getProfile().getMaxBudget()) {
    // Block purchase, show warning
}

// Budget decrease on checkout
buyer.getProfile().setMaxBudget(currentBudget - totalPrice);

// Budget restoration on cancellation
buyer.getProfile().setMaxBudget(currentBudget + orderPrice);
```

### Order Lifecycle Management

```
1. PENDING    → Buyer creates order, awaits seller response
2. ACCEPTED   → Seller approves, ready for transaction
3. COMPLETED  → Buyer confirms receipt, earns points
4. REJECTED   → Seller declines request
5. CANCELLED  → Buyer cancels order
```

### Intelligent Listing Status Handling

```java
// Complete: Only mark as Sold if still Reserved
if (listing != null && "Reserved".equals(listing.getStatus())) {
    listing.setStatus("Sold");
} else {
    // Don't modify removed/banned listings
}

// Cancel: Only restore if still Reserved
if (listing != null && "Reserved".equals(listing.getStatus())) {
    listing.setStatus("Approved");
} else {
    // Don't modify, but still restore buyer's budget
}
```

### Input Validation

**PersonalJPanel validates:**
- Full Name: 2-50 characters, letters and spaces only
- Email: Valid email format
- Phone: 10-15 digits
- Budget: Non-negative number

### Reward Points System

```java
// Earn 5% cashback on completed orders
int points = (int)(orderAmount * 0.05);
buyer.addPoints(points);
```

---

## 🗂️ Data Management

### Directory Pattern

All data is managed through centralized directories:

```java
EcoSystem (Singleton)
├── UserAccountDirectory    // All user accounts
├── ListingDirectory        // All product listings
├── OrderDirectory          // All orders
├── MessageDirectory        // All messages
└── WorkRequestDirectory    // All work requests
```

**Benefits:**
- ✅ Single source of truth
- ✅ Easy data retrieval
- ✅ Consistent data access
- ✅ Supports future database migration

### CSV Persistence

```java
// Save all accounts to CSV
userAccountDAO.saveAll(
    system.getUserAccountDirectory().getUserAccounts()
);

// Load all accounts from CSV
List<UserAccount> accounts = userAccountDAO.loadAll();
```

---

## 🔒 Security Features

### Authentication
- Password hashing (placeholder for production implementation)
- Session management with login tracking
- Role-based access control

### Authorization
- Each role has specific work areas
- Organization-level permission checks
- WorkRequest sender/receiver validation

### Data Validation
- Input sanitization on all forms
- Email format validation
- Phone number validation
- Budget range checking

---

## 🐛 Known Issues & Solutions

### Issue 1: Budget=0 allows unlimited purchases
**Solution**: Set default budget or block purchases when budget=0
**File**: `ShoppingCartWorkArea_Budget0Block.java`

### Issue 2: Duplicate user accounts in CSV
**Solution**: Clear directory before loading
**File**: `UserAccountService.java` - add `clear()` in `loadAllUsers()`

### Issue 3: Listing status conflicts on order cancel
**Solution**: Only restore listing if status="Reserved"
**File**: `TrackingOrderStatus_Fixed.java`

---

## 🚀 Future Enhancements

### Phase 2 (Planned)
- [ ] Database migration (MySQL/PostgreSQL)
- [ ] Real-time push notifications
- [ ] Advanced search with Elasticsearch
- [ ] Image optimization and storage

### Phase 3 (Future)
- [ ] Mobile application (Android/iOS)
- [ ] Web version (Spring Boot + React)
- [ ] Payment gateway integration
- [ ] Machine learning recommendations
- [ ] Multi-language support

---

## 📖 Documentation Files

### Code Documentation
- JavaDoc comments in all classes
- Inline comments for complex logic
- README files in each package

---

## 🙏 Acknowledgments

**Course**: INFO 5100 - Application Engineering and Development  
**Instructor**: Professor Bugrara, K  
**Institution**: Northeastern University  
**Semester**: Fall 2025

**Special Thanks:**
- Teaching assistants for technical guidance
- Classmates for code review and feedback
- Open source libraries (Faker, Swing components)

---

## 📄 License

**Academic Project** - For educational purposes only.

This project is developed as part of the INFO 5100 course curriculum at Northeastern University. 

**Not for commercial use or distribution.**

Copyright © 2025 Group 17. All rights reserved.

---

<div align="center">

**Built with ❤️ by Group 17**

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-007396?style=for-the-badge&logo=java&logoColor=white)

*Northeastern University - Fall 2025*

</div>
