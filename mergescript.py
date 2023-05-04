import pandas as pd

# Load datasets
df1 = pd.read_csv('datasets/HeadsetData2023-05-03_23-33-26_EPOCX_182325_2023.05.03T23.33.29.07.00.md.pm.bp.csv')
df2 = pd.read_csv('datasets/EyeTracking2023-05-03_23-33-26.csv')

df1['Timestamp'] = (df1['Timestamp']*1000).astype(int)
df2['Timestamp'] = df2['Timestamp'].astype(int)

min_val = df1['Timestamp'].min()
max_val = df1['Timestamp'].max()

print(f"min: {min_val} max: {max_val}")

# Select the rows in df1 where common_col is within the range [min_val, max_val]
df2 = df2.loc[(df2['Timestamp'] >= min_val) & (df2['Timestamp'] <= max_val)]

print(f"eye tracker min: {df2['Timestamp'].min()}")

# Merge datasets on common column
merged_df = pd.merge(df1, df2, on='Timestamp', how='outer')

# Sort merged dataframe and reset index
merged_df = merged_df.sort_values('Timestamp')

# Fill missing values with last known values
merged_df = merged_df.fillna(method='ffill')

# Save merged and filled dataframe to new CSV file
merged_df.to_csv('datasets/merged_dataset.csv', index=False)
