import os
import re
from shutil import move
from os import listdir
from os.path import isfile, join

# When overwrite is on, all enum files are set to the specified import path
is_overwrite = False
import_path = os.path.join(os.getcwd(), "..", "..", "src", "data", "icons")
enum_file_path = os.path.join(os.getcwd(), "..", "mil", "af", "eglin", "ccf", "rt", "fx", "icons", "svg", "SvgIcons.java")

enum_file = open(enum_file_path, 'r') 
lines = enum_file.readlines() 

# Get all files currently in the enum file
current_files = []
if (not is_overwrite):
    is_enum_found = False
    for line in lines: 
        trimmed_line = line.strip()
        if (is_enum_found):
            enum = line.strip()
            file_name = (re.findall(r'"([^"]*)"', trimmed_line))
            current_files.append(file_name[0])

        if (not is_enum_found and trimmed_line == "{"):
            is_enum_found = True
        elif (is_enum_found and trimmed_line.endswith(";")):
            break

# Get all files in the import directory
import_files = [file for file in listdir(import_path) if isfile(join(import_path, file))]
files_to_import = []
for file in import_files:
    files_to_import.append(file)

# Combine the current and imported files, and sort
current_files.extend(import_files)
current_files = list(dict.fromkeys(current_files))
current_files.sort()

new_lines = []
for file in current_files:
    file_no_extension, extension = file.split(".")
    all_caps_filename = file_no_extension.upper()
    enum_name = all_caps_filename.replace("-", "_")

    if (not current_files[-1] == file):
        format_template = '    {enum_name_s}("{file_s}"),'
    else:
        format_template = '    {enum_name_s}("{file_s}");'
    new_lines.append(format_template.format(enum_name_s=enum_name, file_s = file))

is_enum_found = False
is_enum_replaced = False
new_content = ""
index = 0
for line in lines: 
    content_line = line
    if (not is_enum_replaced):
        if (is_enum_found):
            content_line = line.replace(line, new_lines[index])
            content_line += "\n"
            index += 1

        trimmed_line = line.strip()
        if (not is_enum_found and trimmed_line == "{"):
            is_enum_found = True
        elif (is_enum_found and trimmed_line.endswith(";")):
            remaining_lines = new_lines[index:]
            for new_line in remaining_lines:
                content_line += new_line
                content_line += "\n"
                is_enum_replaced = True
                is_enum_found = False
                index += 1
    new_content += content_line

# print(new_content)
writing_file = open(enum_file_path, "w")
writing_file.write(new_content)
writing_file.close()
