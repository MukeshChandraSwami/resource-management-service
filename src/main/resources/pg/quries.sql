CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS resource_data (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    account_mapping_id UUID NOT NULL,
    resource_type TEXT NOT NULL,
    img_url TEXT NOT NULL,
    title TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_resource_data_id ON resource_data(id);
CREATE INDEX idx_resource_data_account_mapping_id ON resource_data(account_mapping_id);
CREATE INDEX idx_resource_data_id_account_mapping_id ON resource_data(id, account_mapping_id);

DROP TABLE resource_data;
