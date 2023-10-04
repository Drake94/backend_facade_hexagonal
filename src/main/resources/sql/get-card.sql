SELECT account_number, card_number, type, description_type, status, description_status
FROM card
WHERE card_number = :cardNumber
