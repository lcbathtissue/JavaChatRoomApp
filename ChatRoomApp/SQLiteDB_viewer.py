import os
import sqlite3

def clear_terminal():
    if os.name == 'posix':
        os.system('clear')
    elif os.name == 'nt':
        os.system('cls')

def display_table_data(table_name, cursor):
    print(f"\n{table_name}")
    cursor.execute(f"SELECT name FROM pragma_table_info('{table_name}');")
    column_names = [column[0] for column in cursor.fetchall()]
    print(f"  - ({', '.join(column_names)})")

    cursor.execute(f"SELECT * FROM {table_name};")
    records = cursor.fetchall()
    for record in records:
        print(f"  - {record}")

connection = sqlite3.connect('ChatRoomAppDB.sqlite')
cursor = connection.cursor()

while True:
    clear_terminal()
    cursor.execute("SELECT name FROM sqlite_master WHERE type='table';")
    table_names = cursor.fetchall()
    for table in table_names:
        table_name = table[0]
        display_table_data(table_name, cursor)

    user_input = input("Press Enter to continue, or type 'exit' to quit: ")
    if user_input.lower() == 'exit':
        break

cursor.close()
connection.close()
