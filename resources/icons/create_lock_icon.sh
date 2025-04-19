#!/bin/bash

# Check if ImageMagick is installed
if command -v convert >/dev/null 2>&1; then
  echo "Creating lock icon..."
  
  # Create a blue lock icon with transparent background
  convert -size 128x128 xc:none -fill "#2196F3" -draw "roundrectangle 32,16 96,112 16,16" -draw "roundrectangle 44,48 84,112 8,8" -draw "circle 64,48 64,24" lock_icon.png
  
  echo "Icon created: lock_icon.png"
else
  echo "ImageMagick is not installed. Please install it to generate icons."
  echo "Creating a placeholder icon file instead."
  
  # Create an empty file as placeholder
  touch lock_icon.png
fi

chmod +x create_lock_icon.sh 