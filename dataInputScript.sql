INSERT INTO Menu(MenuName)
VALUES
('Lunch'),

INSERT INTO Menu_Item(ItemName, Description, Price, Category)
VALUES
('Cheeseburger', 'Hamburger topped with american cheese, tomato, pickles, and lettuce, served with a side of fries', 12.99, 'Entree'),
('Soft Drink', 'Coke, Diet Coke, Coke Zero, Dr. Pepper, Sprite, Rootbeer', 2.79, 'Beverage'),
('Lemonade', 'Strawberry, Watermelon, Original', 2.39, 'Beverage'),
('Chicken Tenders', '3 chicken strips served with a side of fries', 10.99, 'Entree'),
('Salad', 'Lettuce, parmesan cheese, tomato and croutons topped with your choice of Caesar or Ranch dressing', 4.99, 'Appetizer'),
('Buffalo Wings', 'Seasoned chicken wings tossed in your choice of Mild or Hot sauce and served with 2 sides of Ranch or Bleu Cheese', 6.99, 'Appetizer');

INSERT INTO Ingredient(IngredientName, QuantityInStock, UnitOfQuantity)
VALUES
('American Cheese', 50, 'slices'),
('Lettuce', 195, 'pieces'),
('Tomato', 56, 'slices'),
('Pickle', 60, 'slices'),
('Burger Patty', 65, 'patties'),
('Burger Bun', 150, 'buns'),
('Chicken Strips', 10, 'bags'),
('Wings', 5, 'bags'),
('Ranch Dressing', 8, 'bottles'),
('Caesar Dressing', 4, 'bottles'),
('Crouton', 5, 'bags'),
('Parmesan Cheese', 2, 'bags'),
('French Fries', 15, 'bags'),
('Buffalo Dressing', 5, 'bottles'),
('Bleu Cheese Dressing', 5, 'bottles'),
('Mild Sauce', 3, 'bottles'),
('Hot Sauce', 3, 'bottles');

INSERT INTO Employee(EmployeeId, EmployeeName)
VALUES
(123, 'John Smith'),
(456, 'Jane Doe'),
(789, 'Jack Baker'),
(149, 'John Smith'),
(135, 'Julia Walker');

INSERT INTO Dining_Table(TableID, Status, Capacity, EmployeeID, OrderID, Date, Time)
VALUES
(1, 'Available', 4, 123, 10000, '2025-11-08', '13:36:30'),
(2, 'Occupied', 6, 456, 10001, '2025-11-08', '13:38:00'),
(3, 'Available', 8, 789, 10002, '2025-11-08', '13:45:37'),
(4, 'Occupied', 4, 789, 10003, '2025-11-08', '13:50:15'),
(5, 'Available', 4, 149, 10004, '2025-11-08', '13:55:24');

INSERT INTO Manages(ManagerID, EmployeeID)
VALUES
(135, 123),
(135, 456),
(135, 789);

INSERT INTO Menu_Connector(MenuName, ItemName)
VALUES
('Lunch', 'Cheeseburger'),
('Lunch', 'Chicken Tenders'),
('Lunch', 'Soft Drink'),
('Lunch', 'Lemonade'),
('Lunch', 'Salad'),
('Lunch', 'Buffalo Wings');

INSERT INTO Recipe(ItemName, IngredientName, IQuantity)
VALUES
('Cheeseburger', 'American Cheese', 1),
('Cheeseburger', 'Tomato', 2),
('Cheeseburger', 'Pickle', 2),
('Cheeseburger', 'Lettuce', 1),
('Cheeseburger', 'Burger Patty', 1),
('Cheeseburger', 'Burger Bun', 2),
('Cheeseburger', 'French Fries', 0.2),
('Chicken Tenders', 'French Fries', 0.2),
('Chicken Tenders', 'Chicken Strips', 0.1),
('Salad', 'Lettuce', 15),
('Salad', 'Parmesan Cheese', 0.1),
('Salad', 'Tomato', 3),
('Salad', 'Crouton', 0.1),
('Buffalo Wings', 'Wings', 0.1),
('Buffalo Wings', 'Buffalo Dressing', 0.05);

INSERT INTO Ordered_Item(OrderID, ItemName, MQuantity)
VALUES
(10001, 'Salad', 1),
(10001, 'Buffalo Wings', 1),
(10001, 'Cheeseburger', 4),
(10001, 'Chicken Tenders', 2),
(10001, 'Soft Drink', 4),
(10001, 'Lemonade', 2),
(10003, 'Cheeseburger', 2),
(10003, 'Soft Drink', 3),
(10003, 'Chicken Tenders', 1);
