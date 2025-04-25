#!/usr/bin/env python3
import os
import sys
import argparse
import subprocess
import shutil
from pathlib import Path

def check_ffmpeg():
    """Verifica se o FFmpeg está instalado no sistema."""
    if shutil.which("ffmpeg") is None:
        print("Erro: FFmpeg não encontrado. Por favor, instale o FFmpeg para usar este script.")
        print("  Ubuntu/Debian: sudo apt-get install ffmpeg")
        print("  macOS: brew install ffmpeg")
        print("  Windows: baixe do site oficial ou use chocolatey: choco install ffmpeg")
        sys.exit(1)

def get_video_info(video_path):
    """Obtém informações básicas do vídeo usando FFmpeg."""
    try:
        cmd = [
            "ffprobe",
            "-v", "error",
            "-select_streams", "v:0",
            "-show_entries", "stream=width,height,duration,bit_rate",
            "-of", "csv=p=0",
            video_path
        ]
        result = subprocess.run(cmd, capture_output=True, text=True, check=True)
        info = result.stdout.strip().split(",")
        if len(info) >= 3:
            return {
                "width": info[0],
                "height": info[1],
                "duration": info[2],
                "bitrate": info[3] if len(info) > 3 else "Desconhecido"
            }
        return {"error": "Formato de saída inesperado"}
    except subprocess.CalledProcessError as e:
        return {"error": f"Erro ao obter informações do vídeo: {e}"}

def get_file_size_mb(file_path):
    """Retorna o tamanho do arquivo em MB."""
    size_bytes = os.path.getsize(file_path)
    return size_bytes / (1024 * 1024)

def compress_video(input_path, output_path, codec="libx264", crf=23, preset="medium", scale=None):
    """
    Comprime o vídeo usando FFmpeg.

    Args:
        input_path (str): Caminho do arquivo de entrada
        output_path (str): Caminho do arquivo de saída
        codec (str): Codec de vídeo a ser usado (libx264, libx265, etc.)
        crf (int): Fator de taxa constante (0-51, menor = melhor qualidade)
        preset (str): Preset de compressão (ultrafast, superfast, ..., veryslow)
        scale (str): Escala de resolução (ex: 1280:720) ou None para manter original

    Returns:
        bool: True se a compressão foi bem-sucedida, False caso contrário
    """
    try:
        # Prepara os argumentos do FFmpeg
        cmd = ["ffmpeg", "-i", input_path]

        # Adiciona parâmetros de vídeo
        cmd.extend(["-c:v", codec])
        cmd.extend(["-crf", str(crf)])
        cmd.extend(["-preset", preset])

        # Adiciona escala de resolução, se especificada
        if scale:
            cmd.extend(["-vf", f"scale={scale}"])

        # Configura o áudio (recodifica para AAC com bitrate reduzido)
        cmd.extend(["-c:a", "aac", "-b:a", "128k"])

        # Adiciona o caminho de saída e opção para sobrescrever
        cmd.extend(["-y", output_path])

        print(f"Executando comando: {' '.join(cmd)}")
        print("\nIniciando compressão de vídeo...")

        # Executa o FFmpeg
        process = subprocess.Popen(
            cmd,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            universal_newlines=True
        )

        # Mostra progresso
        for line in process.stdout:
            if "frame=" in line and "fps=" in line:
                sys.stdout.write("\r" + line.strip())
                sys.stdout.flush()

        # Aguarda o término do processo
        process.wait()
        print("\n")

        if process.returncode == 0:
            return True
        else:
            print(f"Erro na compressão: FFmpeg retornou código {process.returncode}")
            return False

    except Exception as e:
        print(f"Erro ao comprimir o vídeo: {str(e)}")
        return False

def main():
    """Função principal do programa."""
    parser = argparse.ArgumentParser(description="Compressor de vídeo usando FFmpeg")
    parser.add_argument("input", help="Caminho do arquivo de vídeo de entrada")
    parser.add_argument("output", help="Caminho do arquivo de vídeo de saída")
    parser.add_argument("--codec", default="libx264", help="Codec de vídeo (libx264, libx265, libvpx-vp9)")
    parser.add_argument("--crf", type=int, default=23, help="Fator de qualidade (0-51, menor = melhor qualidade)")
    parser.add_argument("--preset", default="medium",
                        help="Preset de compressão (ultrafast, superfast, veryfast, faster, fast, medium, slow, slower, veryslow)")
    parser.add_argument("--scale", help="Escala de resolução (ex: 1280:720)")

    args = parser.parse_args()

    # Verifica se o FFmpeg está instalado
    check_ffmpeg()

    # Verifica se o arquivo de entrada existe
    if not os.path.isfile(args.input):
        print(f"Erro: Arquivo de entrada '{args.input}' não encontrado.")
        sys.exit(1)

    # Cria diretório de saída se não existir
    output_dir = os.path.dirname(args.output)
    if output_dir and not os.path.exists(output_dir):
        os.makedirs(output_dir)

    # Exibe informações do arquivo de entrada
    input_size_mb = get_file_size_mb(args.input)
    print(f"Arquivo de entrada: {args.input}")
    print(f"Tamanho: {input_size_mb:.2f} MB")

    # Obtém informações do vídeo
    video_info = get_video_info(args.input)
    if "error" not in video_info:
        print(f"Resolução: {video_info['width']}x{video_info['height']}")
        print(f"Duração: {float(video_info['duration']):.2f} segundos")
        print(f"Bitrate: {video_info['bitrate']} bps")

    # Exibe configurações de compressão
    print("\nConfigurações de compressão:")
    print(f"Codec: {args.codec}")
    print(f"Fator de qualidade (CRF): {args.crf}")
    print(f"Preset: {args.preset}")
    print(f"Escala: {args.scale if args.scale else 'Original'}")

    # Comprime o vídeo
    if compress_video(args.input, args.output, args.codec, args.crf, args.preset, args.scale):
        # Exibe resultados
        if os.path.exists(args.output):
            output_size_mb = get_file_size_mb(args.output)
            reduction = (1 - (output_size_mb / input_size_mb)) * 100

            print("\nCompressão concluída com sucesso!")
            print(f"Arquivo de saída: {args.output}")
            print(f"Tamanho: {output_size_mb:.2f} MB")
            print(f"Redução: {reduction:.2f}%")
        else:
            print("\nCompressão aparentemente concluída, mas o arquivo de saída não foi encontrado.")
    else:
        print("\nFalha na compressão do vídeo.")

if __name__ == "__main__":
    main()