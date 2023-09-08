from transformers import AutoTokenizer, AutoModel
import torch
import torch.nn.functional as F
import numpy as np


tokenizer = AutoTokenizer.from_pretrained(
    "sentence-transformers/distiluse-base-multilingual-cased-v1"
)
model = AutoModel.from_pretrained(
    "sentence-transformers/distiluse-base-multilingual-cased-v1"
)


def mean_pooling(model_output, attention_mask):
    token_embeddings = model_output[0]
    input_mask_expanded = (
        attention_mask.unsqueeze(-1).expand(token_embeddings.size()).float()
    )
    return torch.sum(token_embeddings * input_mask_expanded, 1) / torch.clamp(
        input_mask_expanded.sum(1), min=1e-9
    )


def get_embedding(query):
    tokenized_inp = tokenizer(
        query, padding=True, truncation=True, return_tensors="pt"
    )

    with torch.no_grad():
        model_output = model(**tokenized_inp)

    sentence_embeddings = mean_pooling(
        model_output, tokenized_inp["attention_mask"]
    )
    sentence_embeddings = F.normalize(sentence_embeddings, p=2, dim=1)

    return torch.squeeze(sentence_embeddings).numpy()


if __name__ == "__main__":
    r = get_embedding("Как менялся чистый ком доход по дебетовкам за год")
    print(len(r))
